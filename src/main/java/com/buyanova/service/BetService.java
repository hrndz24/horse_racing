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
import com.buyanova.specification.impl.bet.FindBetsByUserId;
import com.buyanova.specification.impl.odds.FindOddsById;
import com.buyanova.specification.impl.user.FindUserById;
import com.buyanova.validator.BetValidator;

import java.math.BigDecimal;
import java.util.List;

public enum BetService {
    INSTANCE;

    private RepositoryFactory factory = RepositoryFactory.INSTANCE;
    private BetRepository betRepository = factory.getBetRepository();
    private UserRepository userRepository = factory.getUserRepository();
    private OddsRepository oddsRepository = factory.getOddsRepository();

    private BetValidator betValidator = new BetValidator();

    public void addBet(Bet bet) throws ServiceException {
        if (bet == null) {
            throw new ServiceException("Null bet");
        }
        validateBetFields(bet);
        User user = getUser(bet.getUserId());
        checkUserHasEnoughMoneyToBet(user, bet.getSum());
        tryAddBetToDataSource(bet);
    }

    private User getUser(int userId) throws ServiceException {
        try {
            return userRepository.query(new FindUserById(userId)).get(0);
        } catch (RepositoryException e) {
            throw new ServiceException("Failed to get user due to data source problems", e);
        }
    }

    private void tryAddBetToDataSource(Bet bet) throws ServiceException {
        try {
            betRepository.add(bet);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Removes bet from the data source and
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
        tryRemoveBetFromDataSource(bet);
    }

    private void tryRemoveBetFromDataSource(Bet bet) throws ServiceException {
        try {
            betRepository.remove(bet);
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
        User user = getUser(bet.getUserId());
        BigDecimal oldSum = getBetSumFromDataSource(bet.getId());
        BigDecimal sumDifference = bet.getSum().subtract(oldSum);
        checkUserHasEnoughMoneyToBet(user, sumDifference);
        tryUpdateBetInDataSource(bet);
    }

    private BigDecimal getBetSumFromDataSource(int betId) throws ServiceException {
        try {
            return betRepository.query(new FindBetById(betId)).get(0).getSum();
        } catch (RepositoryException e) {
            throw new ServiceException("Failed to get bet due to data source problems", e);
        }
    }

    private void tryUpdateBetInDataSource(Bet bet) throws ServiceException {
        try {
            betRepository.update(bet);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    public List<Bet> getBetsByUser(User user) throws ServiceException {
        if (user == null) {
            throw new ServiceException("Null user");
        }
        try {
            return betRepository.query(new FindBetsByUserId(user.getId()));
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    public Bet getBetById(int betId) throws ServiceException {
        try {
            return betRepository.query(new FindBetById(betId)).get(0);
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

    private void checkUserHasEnoughMoneyToBet(User user, BigDecimal sum) throws ServiceException {
        if (user.getBalance().compareTo(sum) < 0) {
            throw new ServiceException("User does not have enough money to make a bet");
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
