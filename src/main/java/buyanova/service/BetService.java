package buyanova.service;

import buyanova.entity.Bet;
import buyanova.exception.RepositoryException;
import buyanova.exception.ServiceException;
import buyanova.factory.RepositoryFactory;
import buyanova.repository.bet.BetRepository;
import buyanova.repository.odds.OddsRepository;
import buyanova.repository.user.UserRepository;
import buyanova.specification.sql.implementation.odds.FindOddsById;
import buyanova.specification.sql.implementation.user.FindUserById;
import buyanova.validator.BetValidator;

public enum BetService {
    INSTANCE;

    private RepositoryFactory factory = RepositoryFactory.INSTANCE;
    private BetRepository betRepository = factory.getBetRepository();
    private UserRepository userRepository = factory.getUserRepository();
    private OddsRepository oddsRepository = factory.getOddsRepository();

    private BetValidator betValidator = new BetValidator();

    public Bet addBet(Bet bet) throws ServiceException {
        validateBetFields(bet);
        try {
            betRepository.add(bet);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
        return bet;
    }

    public void removeBet(Bet bet) throws ServiceException {
        validateBetFields(bet);
        try {
            betRepository.remove(bet);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    public void updateBet(Bet bet) throws ServiceException {
        validateBetFields(bet);
        try {
            betRepository.update(bet);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    private void validateBetFields(Bet bet) throws ServiceException {
        if (bet == null) {
            throw new ServiceException("Null bet");
        }

        if (bet.getSum() == null) {
            throw new ServiceException("Null sum in bet");
        }
        if (!betValidator.isSumPositive(bet.getSum())) {
            throw new ServiceException("Negative sum in bet");
        }

        try {
            if (userRepository.query(new FindUserById(bet.getUserId())).isEmpty()) {
                throw new ServiceException("User with such id does not exist");
            }
            if (oddsRepository.query(new FindOddsById(bet.getOddsId())).isEmpty()) {
                throw new ServiceException("Odds with such id do not exist");
            }
        } catch (RepositoryException e) {
            throw new ServiceException("Failed to access users' data", e);
        }
    }
}
