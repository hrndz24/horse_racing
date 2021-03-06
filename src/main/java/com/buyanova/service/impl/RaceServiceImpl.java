package com.buyanova.service.impl;

import com.buyanova.entity.Horse;
import com.buyanova.entity.Race;
import com.buyanova.exception.RepositoryException;
import com.buyanova.exception.ServiceException;
import com.buyanova.factory.RepositoryFactory;
import com.buyanova.repository.horse.HorseRepository;
import com.buyanova.repository.race.RaceRepository;
import com.buyanova.service.RaceService;
import com.buyanova.specification.impl.horse.FindHorseById;
import com.buyanova.specification.impl.horse.FindHorsesPerformingInRace;
import com.buyanova.specification.impl.race.*;
import com.buyanova.validator.RaceValidator;

import java.util.List;

public enum RaceServiceImpl implements RaceService {

    INSTANCE;

    private RepositoryFactory factory = RepositoryFactory.INSTANCE;
    private RaceRepository raceRepository = factory.getRaceRepository();
    private HorseRepository horseRepository = factory.getHorseRepository();

    private RaceValidator raceValidator = new RaceValidator();

    public void addRace(Race race) throws ServiceException {
        if (race == null) {
            throw new ServiceException("Null race");
        }
        validateRaceFields(race);
        tryAddRaceToDataSource(race);
    }

    private void tryAddRaceToDataSource(Race race) throws ServiceException {
        try {
            raceRepository.add(race);
        } catch (RepositoryException e) {
            throw new ServiceException("Failed to add race due to data source problems", e);
        }
    }

    public void setRaceResults(Race race) throws ServiceException {
        if (race == null) {
            throw new ServiceException("Null race");
        }
        checkHorseWinnerIsNotSet(race);
        checkHorseWinnerPerformedInRace(race);
        trySetRaceResults(race);
        updateHorsesStatistics(race);
    }

    private void checkHorseWinnerIsNotSet(Race race) throws ServiceException {
        try {
            if (raceRepository.query(new FindRaceById(race.getId())).get(0).getHorseWinnerId() != 0)
                throw new ServiceException("Horse winner is already set");
        } catch (RepositoryException e) {
            throw new ServiceException("Failed to get horse winner due to data source problems", e);
        }
    }

    private void checkHorseWinnerPerformedInRace(Race race) throws ServiceException {
        Horse horseWinner = getHorseIfExists(race.getHorseWinnerId());
        List<Horse> horsesInRace = getHorsesFromRace(race.getId());
        if (!horsesInRace.contains(horseWinner)) {
            throw new ServiceException("Horse with such id did not perform in the race");
        }
    }

    private Horse getHorseIfExists(int horseId) throws ServiceException {
        try {
            List<Horse> horses = horseRepository.query(new FindHorseById(horseId));
            if (horses.isEmpty())
                throw new ServiceException("Horse with such id does not exist");
            return horses.get(0);
        } catch (RepositoryException e) {
            throw new ServiceException("Failed to get horse due to data source problems", e);
        }
    }

    private void trySetRaceResults(Race race) throws ServiceException {
        try {
            raceRepository.setRaceResults(race);
        } catch (RepositoryException e) {
            throw new ServiceException("Failed to set race results due to data source problems", e);
        }
    }

    private void updateHorsesStatistics(Race race) throws ServiceException {
        List<Horse> horses = getHorsesFromRace(race.getId());
        for (Horse horse : horses) {
            updateHorseStatisticsBasedOnRaceResults(horse, race.getHorseWinnerId());
        }
    }

    private List<Horse> getHorsesFromRace(int raceId) throws ServiceException {
        try {
            return horseRepository.query(new FindHorsesPerformingInRace(raceId));
        } catch (RepositoryException e) {
            throw new ServiceException("Failed to get horses of the race due to data source problems", e);
        }
    }

    private void updateHorseStatisticsBasedOnRaceResults(Horse horse, int horseWinnerId) throws ServiceException {
        if (horse.getId() == horseWinnerId) {
            horse.incrementWonRacesNumber();
        } else {
            horse.incrementLostRacesNumber();
        }
        tryUpdateHorseInDataSource(horse);
    }

    private void tryUpdateHorseInDataSource(Horse horse) throws ServiceException {
        try {
            horseRepository.update(horse);
        } catch (RepositoryException e) {
            throw new ServiceException("Failed to update horse due to data source problems", e);
        }
    }

    public void updateRace(Race race) throws ServiceException {
        if (race == null) {
            throw new ServiceException("Null race");
        }
        validateRaceFields(race);
        tryUpdateRaceInDataSource(race);
    }

    private void tryUpdateRaceInDataSource(Race race) throws ServiceException {
        try {
            raceRepository.update(race);
        } catch (RepositoryException e) {
            throw new ServiceException("Failed to update race due to data source problems", e);
        }
    }

    public void removeRace(Race race) throws ServiceException {
        if (race == null) {
            throw new ServiceException("Null race");
        }
        tryRemoveRaceFromDataSource(race);
    }

    private void tryRemoveRaceFromDataSource(Race race) throws ServiceException {
        try {
            raceRepository.remove(race);
        } catch (RepositoryException e) {
            throw new ServiceException("Failed to remove race due to data source problems", e);
        }
    }

    public List<Race> getRacesWithoutResults() throws ServiceException {
        try {
            return raceRepository.query(new FindRacesWithoutResults());
        } catch (RepositoryException e) {
            throw new ServiceException("Failed to get races due to data source problems", e);
        }
    }

    public void addHorseToRace(int horseId, int raceId) throws ServiceException {
        try {
            raceRepository.addHorseToRace(horseId, raceId);
        } catch (RepositoryException e) {
            throw new ServiceException("Failed to add horse to race due to data source problems", e);
        }
    }

    public void removeHorseFromRace(int horseId, int raceId) throws ServiceException {
        try {
            raceRepository.removeHorseFromRace(horseId, raceId);
        } catch (RepositoryException e) {
            throw new ServiceException("Failed to remove horse from race due to data source problems", e);
        }
    }

    public List<Race> getUpcomingRaces() throws ServiceException {
        try {
            return raceRepository.query(new FindRacesAfterCurrentDate());
        } catch (RepositoryException e) {
            throw new ServiceException("Failed to get upcoming races due to data source problems", e);
        }
    }

    public List<Race> getUpcomingRacesWithoutOdds() throws ServiceException {
        try {
            return raceRepository.query(new FindRacesAfterCurrentDateWithoutOdds());
        } catch (RepositoryException e) {
            throw new ServiceException("Failed to get upcoming races without odds due to data source problems", e);
        }
    }

    public List<Race> getPastRacesSubList(int indexFrom, int size) throws ServiceException {
        if (indexFrom < 0 || size < 0) {
            throw new ServiceException("Invalid index from or size number");
        }
        try {
            return raceRepository.query(new FindPastRacesSortedDescendingByDateWithLimitAndOffset(indexFrom, size));
        } catch (RepositoryException e) {
            throw new ServiceException("Failed to get past races due to data source problems", e);
        }
    }

    @Override
    public int getPastRacesTotalNumber() throws ServiceException {
        try {
            return raceRepository.getNumberOfPastRacesRecords();
        } catch (RepositoryException e) {
            throw new ServiceException("Failed to get number of past races due to data source problems", e);
        }
    }

    public Race getRaceById(int raceId) throws ServiceException {
        return getFirstRaceByIdIfExists(raceId);
    }

    private Race getFirstRaceByIdIfExists(int raceId) throws ServiceException {
        try {
            List<Race> races = raceRepository.query(new FindRaceById(raceId));
            if (races.isEmpty()) {
                throw new ServiceException("Race with such id doesn't exist");
            }
            return races.get(0);
        } catch (RepositoryException e) {
            throw new ServiceException("Failed to get race due to data source problems", e);
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