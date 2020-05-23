package com.buyanova.service.impl;

import com.buyanova.entity.*;
import com.buyanova.exception.RepositoryException;
import com.buyanova.exception.ServiceException;
import com.buyanova.factory.RepositoryFactory;
import com.buyanova.repository.horse.HorseRepository;
import com.buyanova.repository.odds.OddsRepository;
import com.buyanova.repository.race.RaceRepository;
import com.buyanova.repository.user.UserRepository;
import com.buyanova.service.OddsService;
import com.buyanova.specification.impl.horse.FindHorseById;
import com.buyanova.specification.impl.horse.FindHorsesPerformingInRace;
import com.buyanova.specification.impl.odds.FindOddsByHorseIdAndRaceId;
import com.buyanova.specification.impl.odds.FindOddsById;
import com.buyanova.specification.impl.odds.FindOddsByRace;
import com.buyanova.specification.impl.race.FindRaceById;
import com.buyanova.specification.impl.user.FindUserById;
import com.buyanova.validator.OddsValidator;
import com.buyanova.validator.RaceValidator;

import java.util.List;

public enum OddsServiceImpl implements OddsService {

    INSTANCE;

    private RepositoryFactory factory = RepositoryFactory.INSTANCE;
    private UserRepository userRepository = factory.getUserRepository();
    private OddsRepository oddsRepository = factory.getOddsRepository();
    private RaceRepository raceRepository = factory.getRaceRepository();
    private HorseRepository horseRepository = factory.getHorseRepository();

    private OddsValidator oddsValidator = new OddsValidator();
    private RaceValidator raceValidator = new RaceValidator();

    public void addOdds(Odds odds) throws ServiceException {
        if (odds == null) {
            throw new ServiceException("Null odds");
        }
        validateOddsFields(odds);
        tryAddOddsToDataSource(odds);
    }

    private void tryAddOddsToDataSource(Odds odds) throws ServiceException {
        try {
            oddsRepository.add(odds);
        } catch (RepositoryException e) {
            throw new ServiceException("Failed to add odds due to data source problems", e);
        }
    }

    public void updateOdds(Odds odds) throws ServiceException {
        if (odds == null) {
            throw new ServiceException("Null odds");
        }
        if (!(oddsValidator.areOddsPositive(odds.getOddsAgainst())
                && oddsValidator.areOddsPositive(odds.getOddsInFavour()))) {
            throw new ServiceException("Negative odds numbers");
        }
        tryUpdateOddsInDataSource(odds);
    }

    private void tryUpdateOddsInDataSource(Odds odds) throws ServiceException {
        try {
            oddsRepository.update(odds);
        } catch (RepositoryException e) {
            throw new ServiceException("Failed to update odds due to data source problems", e);
        }
    }

    public List<Odds> getOddsForRace(int raceId) throws ServiceException {
        try {
            return oddsRepository.query(new FindOddsByRace(raceId));
        } catch (RepositoryException e) {
            throw new ServiceException("Failed to get odds due to data source problems", e);
        }
    }

    public Odds getOddsForHorseInRace(int horseId, int raceId) throws ServiceException {
        try {
            return oddsRepository.query(new FindOddsByHorseIdAndRaceId(horseId, raceId)).get(0);
        } catch (RepositoryException e) {
            throw new ServiceException("Failed to get odds due to data source problems", e);
        }
    }

    public Odds getOddsById(int oddsId) throws ServiceException {
        try {
            return oddsRepository.query(new FindOddsById(oddsId)).get(0);
        } catch (RepositoryException e) {
            throw new ServiceException("Failed to get odds due to data source problems", e);
        }
    }

    private void validateOddsFields(Odds odds) throws ServiceException {
        checkOddsNumbersArePositive(odds);
        checkBookmakerIdBelongsToBookmaker(odds.getBookmakerId());
        checkRaceHasNotHappened(odds.getRaceId());
        Horse horse = getHorseIfExists(odds.getHorseId());
        checkHorsePerformingInRace(odds.getRaceId(), horse.getId());
    }

    private void checkOddsNumbersArePositive(Odds odds) throws ServiceException {
        if (!oddsValidator.areOddsPositive(odds.getOddsInFavour())) {
            throw new ServiceException("Negative odds in favour");
        }
        if (!oddsValidator.areOddsPositive(odds.getOddsAgainst())) {
            throw new ServiceException("Negative odds against");
        }
    }

    private void checkBookmakerIdBelongsToBookmaker(int bookmakerId) throws ServiceException {
        try {
            List<User> bookmakers = userRepository.query(new FindUserById(bookmakerId));
            if (bookmakers.isEmpty()) {
                throw new ServiceException("Bookmaker with such id does not exist");
            }
            if (bookmakers.get(0).getUserRole() != UserRole.BOOKMAKER) {
                throw new ServiceException("Odds with non-existent bookmaker id");
            }
        } catch (RepositoryException e) {
            throw new ServiceException("Failed to get bookmaker due to data source problems", e);
        }
    }

    private void checkRaceHasNotHappened(int raceId) throws ServiceException {
        try {
            List<Race> races = raceRepository.query(new FindRaceById(raceId));
            if (races.isEmpty()) {
                throw new ServiceException("Race with such id does not exist");
            }
            if (!raceValidator.isDateAfterNow(races.get(0).getDate())) {
                throw new ServiceException("Race has already happened");
            }
        } catch (RepositoryException e) {
            throw new ServiceException("Failed to get race due to data source problems", e);
        }
    }

    private void checkHorsePerformingInRace(int raceId, int horseId) throws ServiceException {
        Horse horse = getHorseIfExists(horseId);
        List<Horse> horsesInRace = getHorsesFromRace(raceId);
        if (!horsesInRace.contains(horse)) {
            throw new ServiceException("Horse with such id did not perform in the race");
        }
    }

    private List<Horse> getHorsesFromRace(int raceId) throws ServiceException {
        try {
            return horseRepository.query(new FindHorsesPerformingInRace(raceId));
        } catch (RepositoryException e) {
            throw new ServiceException("Failed to get horses due to data source problems", e);
        }
    }

    private Horse getHorseIfExists(int horseId) throws ServiceException {
        try {
            List<Horse> horses = horseRepository.query(new FindHorseById(horseId));
            if (horses.isEmpty())
                throw new ServiceException("Horse with such id does not exist");
            return horses.get(0);
        } catch (RepositoryException e) {
            throw new ServiceException("Failed to get horse because of data source problems", e);
        }
    }
}
