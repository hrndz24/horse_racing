package com.buyanova.service;

import com.buyanova.entity.*;
import com.buyanova.exception.RepositoryException;
import com.buyanova.exception.ServiceException;
import com.buyanova.factory.RepositoryFactory;
import com.buyanova.repository.bet.BetRepository;
import com.buyanova.repository.horse.HorseRepository;
import com.buyanova.repository.odds.OddsRepository;
import com.buyanova.repository.race.RaceRepository;
import com.buyanova.repository.user.UserRepository;
import com.buyanova.specification.impl.bet.FindLostBetsInRaceByUserId;
import com.buyanova.specification.impl.bet.FindWonBetsInRaceByUserId;
import com.buyanova.specification.impl.horse.FindHorseById;
import com.buyanova.specification.impl.horse.FindHorsesPerformingInRace;
import com.buyanova.specification.impl.odds.FindOddsById;
import com.buyanova.specification.impl.race.FindRaceById;
import com.buyanova.specification.impl.race.FindRacesAfterCurrentDate;
import com.buyanova.specification.impl.race.FindRacesWithoutResults;
import com.buyanova.specification.impl.user.FindUserById;
import com.buyanova.validator.RaceValidator;

import java.math.BigDecimal;
import java.util.List;

public enum RaceService {

    INSTANCE;

    private RepositoryFactory factory = RepositoryFactory.INSTANCE;
    private RaceRepository raceRepository = factory.getRaceRepository();
    private HorseRepository horseRepository = factory.getHorseRepository();
    private UserRepository userRepository = factory.getUserRepository();
    private BetRepository betRepository = factory.getBetRepository();
    private OddsRepository oddsRepository = factory.getOddsRepository();

    private RaceValidator raceValidator = new RaceValidator();

    public void addRace(Race race) throws ServiceException {
        if (race == null) {
            throw new ServiceException("Null race");
        }
        validateRaceFields(race);
        try {
            raceRepository.add(race);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Updates information about the race in the data source.
     * <p>
     * It automatically updates users' and bookmaker's balances
     * according to their bets and odds.
     * <p>
     * Note: by race results we imply setting the horse winner.
     * In case when other information about the race in the data
     * source doesn't match the data stored in {@code race} fields,
     * the latter is in priority and will overwrite information
     * in the data source.
     *
     * @param race race that is to be updated
     * @throws ServiceException if null or non-existent race is passed,
     *                          if horse winner is already specified in the data source
     *                          if horse winner did not perform in the race,
     *                          if a data source access error occurs
     */
    public void setRaceResults(Race race) throws ServiceException {
        if (race == null) {
            throw new ServiceException("Null race");
        }

        checkHorseWinnerIsNotSet(race);
        checkHorseWinnerPerformedInRace(race);
        try {
            raceRepository.update(race);
            List<Bet> wonBets = betRepository.query(new FindWonBetsInRaceByUserId(race.getId()));
            List<Bet> lostBets = betRepository.query(new FindLostBetsInRaceByUserId(race.getId()));

            for (Bet bet : wonBets) {
                payMoneyToUsersWhoWon(bet);
            }
            for (Bet bet : lostBets) {
                withdrawMoneyFromUsersWhoLost(bet);
            }
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    public void removeRace(Race race) throws ServiceException {
        if (race == null) {
            throw new ServiceException("Null race");
        }
        try {
            raceRepository.remove(race);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    public List<Race> getRacesWithoutResults() throws ServiceException {
        try {
            return raceRepository.query(new FindRacesWithoutResults());
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    public void addHorseToRace(int horseId, int raceId) throws ServiceException {
        try {
            raceRepository.addHorseToRace(horseId, raceId);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    public List<Race> getUpcomingRaces() throws ServiceException {
        try {
            return raceRepository.query(new FindRacesAfterCurrentDate());
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    public Race getRaceById(int raceId) throws ServiceException {
        try {
            return raceRepository.query(new FindRaceById(raceId)).get(0);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    private void payMoneyToUsersWhoWon(Bet bet) throws ServiceException {
        try {
            User user = userRepository.query(new FindUserById(bet.getUserId())).get(0);
            Odds odds = oddsRepository.query(new FindOddsById(bet.getOddsId())).get(0);
            User bookmaker = userRepository.query(new FindUserById(odds.getBookmakerId())).get(0);
            int oddsInFavour = odds.getOddsInFavour();
            BigDecimal userWin = bet.getSum().multiply(new BigDecimal(oddsInFavour));
            user.setBalance(user.getBalance().add(userWin));
            bookmaker.setBalance(bookmaker.getBalance().subtract(userWin));
            userRepository.update(user);
            userRepository.update(bookmaker);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    private void withdrawMoneyFromUsersWhoLost(Bet bet) throws ServiceException {
        try {
            User user = userRepository.query(new FindUserById(bet.getUserId())).get(0);
            Odds odds = oddsRepository.query(new FindOddsById(bet.getOddsId())).get(0);
            User bookmaker = userRepository.query(new FindUserById(odds.getBookmakerId())).get(0);
            int oddsAgainst = odds.getOddsAgainst();
            BigDecimal userLoss = bet.getSum().multiply(new BigDecimal(oddsAgainst - 1));
            user.setBalance(user.getBalance().subtract(userLoss));
            bookmaker.setBalance(bookmaker.getBalance().add(userLoss));
            userRepository.update(user);
            userRepository.update(bookmaker);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    private void checkRaceExists(int raceId) throws ServiceException {
        try {
            if (raceRepository.query(new FindRaceById(raceId)).isEmpty()) {
                throw new ServiceException("Race with such id does not exist");
            }
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    private void checkHorseWinnerIsNotSet(Race race) throws ServiceException {
        try {
            if (raceRepository.query(new FindRaceById(race.getId())).get(0).getHorseWinnerId() != 0) {
                throw new ServiceException("Horse winner is already set");
            }
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    private void checkHorseWinnerPerformedInRace(Race race) throws ServiceException {
        try {
            List<Horse> horses = horseRepository.query(new FindHorseById(race.getHorseWinnerId()));
            if (horses.isEmpty()) {
                throw new ServiceException("Horse with such id does not exist");
            }
            Horse horseWinner = horses.get(0);

            List<Horse> horsesInRace = horseRepository.query(new FindHorsesPerformingInRace(race.getId()));
            if (!horsesInRace.contains(horseWinner)) {
                throw new ServiceException("Horse with such id did not perform in the race");
            }
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    private void validateRaceFields(Race race) throws ServiceException {
        if (race.getPrizeMoney() == null) {
            throw new ServiceException("Null race prize money");
        }
        if (!raceValidator.isPrizeMoneyPositive(race.getPrizeMoney())) {
            throw new ServiceException("Negative race prize money");
        }
        if (race.getDate() == null) {
            throw new ServiceException("Null race date");
        }
        if (!raceValidator.isDateAfterNow(race.getDate())) {
            throw new ServiceException("Invalid race date");
        }
        if (!raceValidator.isDistancePositive(race.getDistance())) {
            throw new ServiceException("Negative race distance");
        }
        if (race.getLocation() == null || race.getLocation().isEmpty()) {
            throw new ServiceException("No location in race");
        }
    }
}
