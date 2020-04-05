package buyanova.service;

import buyanova.entity.*;
import buyanova.exception.RepositoryException;
import buyanova.exception.ServiceException;
import buyanova.factory.RepositoryFactory;
import buyanova.repository.horse.HorseRepository;
import buyanova.repository.odds.OddsRepository;
import buyanova.repository.race.RaceRepository;
import buyanova.repository.user.UserRepository;
import buyanova.specification.sql.impl.horse.FindHorseById;
import buyanova.specification.sql.impl.horse.FindHorsesPerformingInRace;
import buyanova.specification.sql.impl.odds.FindOddsById;
import buyanova.specification.sql.impl.odds.FindOddsByRace;
import buyanova.specification.sql.impl.race.FindRaceById;
import buyanova.specification.sql.impl.user.FindUserById;
import buyanova.validator.OddsValidator;
import buyanova.validator.RaceValidator;

import java.util.List;

public enum OddsService {

    INSTANCE;

    private RepositoryFactory factory = RepositoryFactory.INSTANCE;
    private UserRepository userRepository = factory.getUserRepository();
    private OddsRepository oddsRepository = factory.getOddsRepository();
    private RaceRepository raceRepository = factory.getRaceRepository();
    private HorseRepository horseRepository = factory.getHorseRepository();

    private OddsValidator oddsValidator = new OddsValidator();
    private RaceValidator raceValidator = new RaceValidator();

    public void placeOdds(Odds odds) throws ServiceException {
        if (odds == null) {
            throw new ServiceException("Null odds");
        }
        validateOddsFields(odds);

        try {
            oddsRepository.add(odds);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    public void changeOddsNumbers(Odds odds) throws ServiceException {
        if (odds == null) {
            throw new ServiceException("Null odds");
        }
        checkOddsExist(odds);
        if (!(oddsValidator.areOddsPositive(odds.getOddsAgainst())
                && oddsValidator.areOddsPositive(odds.getOddsInFavour()))) {
            throw new ServiceException("Negative odds numbers");
        }
        try {
            oddsRepository.update(odds);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    public void removeOdds(Odds odds) throws ServiceException {
        if (odds == null) {
            throw new ServiceException("Null odds");
        }
        try {
            oddsRepository.remove(odds);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    public List<Odds> getOddsForRace(int raceId) throws ServiceException {
        try {
            return oddsRepository.query(new FindOddsByRace(raceId));
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    private void checkOddsExist(Odds odds) throws ServiceException {
        try {
            if (oddsRepository.query(new FindOddsById(odds.getId())).isEmpty()) {
                throw new ServiceException("Odds with such id do not exist");
            }
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    private void validateOddsFields(Odds odds) throws ServiceException {
        if (!oddsValidator.areOddsPositive(odds.getOddsInFavour())) {
            throw new ServiceException("Negative odds in favour");
        }
        if (!oddsValidator.areOddsPositive(odds.getOddsAgainst())) {
            throw new ServiceException("Negative odds against");
        }
        try {
            List<User> bookmakers = userRepository.query(new FindUserById(odds.getBookmakerId()));
            if (bookmakers.isEmpty()) {
                throw new ServiceException("Bookmaker with such id does not exist");
            }
            if (bookmakers.get(0).getUserRole() != UserRole.BOOKMAKER) {
                throw new ServiceException("Odds with non-existent bookmaker id");
            }

            List<Race> races = raceRepository.query(new FindRaceById(odds.getRaceId()));
            if (races.isEmpty()) {
                throw new ServiceException("Race with such id does not exist");
            }
            if (!raceValidator.isDateAfterNow(races.get(0).getDate())) {
                throw new ServiceException("Race has already happened");
            }

            List<Horse> horses = horseRepository.query(new FindHorseById(odds.getHorseId()));
            if (horses.isEmpty()) {
                throw new ServiceException("Horse with such id does not exist");
            }
            Horse horse = horses.get(0);

            List<Horse> horsesInRace = horseRepository.query(new FindHorsesPerformingInRace(odds.getRaceId()));
            if (!horsesInRace.contains(horse)) {
                throw new ServiceException("Horse with such id does not perform in race with provided id");
            }

        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }
}
