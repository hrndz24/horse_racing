package com.buyanova.service;

import com.buyanova.entity.Bet;
import com.buyanova.entity.User;
import com.buyanova.exception.RepositoryException;
import com.buyanova.exception.ServiceException;
import com.buyanova.factory.RepositoryFactory;
import com.buyanova.repository.bet.BetRepository;
import com.buyanova.repository.odds.OddsRepository;
import com.buyanova.repository.user.UserRepository;
import com.buyanova.specification.impl.bet.FindBetById;
import com.buyanova.specification.impl.odds.FindOddsById;
import com.buyanova.specification.impl.user.FindUserById;
import com.buyanova.validator.BetValidator;

import java.math.BigDecimal;

public enum BetService {
    INSTANCE;

    private RepositoryFactory factory = RepositoryFactory.INSTANCE;
    private BetRepository betRepository = factory.getBetRepository();
    private UserRepository userRepository = factory.getUserRepository();
    private OddsRepository oddsRepository = factory.getOddsRepository();

    private BetValidator betValidator = new BetValidator();

    public Bet makeBet(Bet bet) throws ServiceException {
        if (bet == null) {
            throw new ServiceException("Null bet");
        }
        validateBetFields(bet);
        try {
            User user = userRepository.query(new FindUserById(bet.getUserId())).get(0);
            if (user.getBalance().compareTo(bet.getSum()) < 0) {
                throw new ServiceException("User does not have enough money to make a bet");
            }
            betRepository.add(bet);
            user.setBalance(user.getBalance().subtract(bet.getSum()));
            userRepository.update(user);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
        return bet;
    }

    /** Removes bet form the data source and
     * returns money to the user.
     * <p>
     * Note: checks bet exists so that to prevent
     * replenishment of user's account
     *
     * @param bet bet that is to be removed,
     *            should be present in the data source
     * @throws ServiceException if null or non-existent bet is passed
     *                          or a data source access error occurs
     */
    public void removeBet(Bet bet) throws ServiceException {
        if (bet == null) {
            throw new ServiceException("Null bet");
        }
        checkBetExists(bet);
        try {
            User user = userRepository.query(new FindUserById(bet.getUserId())).get(0);
            betRepository.remove(bet);
            user.setBalance(user.getBalance().add(bet.getSum()));
            userRepository.update(user);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    public void updateBetSum(Bet bet) throws ServiceException {
        if (bet == null) {
            throw new ServiceException("Null bet");
        }
        checkBetExists(bet);
        validateBetFields(bet);
        try {
            User user = userRepository.query(new FindUserById(bet.getUserId())).get(0);
            BigDecimal oldSum = betRepository.query(new FindBetById(bet.getId())).get(0).getSum();
            betRepository.update(bet);
            user.setBalance(user.getBalance().add(oldSum.subtract(bet.getSum())));
            userRepository.update(user);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    private void checkBetExists(Bet bet) throws ServiceException {
        try {
            if (betRepository.query(new FindBetById(bet.getId())).isEmpty()) {
                throw new ServiceException("Bet with such id does not exist");
            }
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    private void validateBetFields(Bet bet) throws ServiceException {
        if (bet.getSum() == null) {
            throw new ServiceException("Null bet sum");
        }
        if (!betValidator.isSumPositive(bet.getSum())) {
            throw new ServiceException("Negative bet sum");
        }

        try {
            if (userRepository.query(new FindUserById(bet.getUserId())).isEmpty()) {
                throw new ServiceException("User with such id does not exist");
            }
            if (oddsRepository.query(new FindOddsById(bet.getOddsId())).isEmpty()) {
                throw new ServiceException("Odds with such id do not exist");
            }
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }
}
