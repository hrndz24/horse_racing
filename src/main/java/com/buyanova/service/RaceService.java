package com.buyanova.service;

import com.buyanova.entity.Race;
import com.buyanova.exception.ServiceException;

import java.util.List;

/**
 * {@code RaceService} interface is used to describe
 * business logic concerning {@code Race} entity.
 *
 * @author Natalie
 * @see com.buyanova.service.impl.RaceServiceImpl
 */
public interface RaceService {

    /**
     * Adds new race to the data source.
     * <p>
     * Note: races with the past date won't be added
     * and an exception will be thrown.
     *
     * @param race race that is to be added
     * @throws ServiceException if null race is passed,
     *                          if race contains invalid fields,
     *                          if a data source access error occurs
     */
    void addRace(Race race) throws ServiceException;

    /**
     * Sets the horse winner of the race in the data source.
     * <p>
     * The method will execute successfully if and only if
     * the horse winner is not specified, otherwise an exception
     * is thrown.
     * <p>
     * It automatically updates users' and bookmaker's balances
     * according to their bets and odds. Horses' statistics is
     * also updated.
     *
     * @param race race which results are to be set
     * @throws ServiceException if null race is passed,
     *                          if horse winner is already specified in the data source
     *                          if horse winner did not perform in the race,
     *                          if a data source access error occurs
     */
    void setRaceResults(Race race) throws ServiceException;

    /**
     * Updates information about the race in the data source.
     * <p>
     * Note: horse winner field should not be considered
     * and changed. To get the information about updating
     * this field see {@link RaceService#setRaceResults(Race)}
     *
     * @param race race that is to be updated
     * @throws ServiceException if null race is passed,
     *                          if race contains invalid fields,
     *                          if a data source access error occurs
     */
    void updateRace(Race race) throws ServiceException;

    /**
     * Removes race from the data source.
     *
     * @param race race that is to be removed
     * @throws ServiceException if null race is passed,
     *                          if a data source access error occurs
     */
    void removeRace(Race race) throws ServiceException;

    /**
     * Returns list of races that have already finished (their date
     * is before the current date), but their horse winners are
     * not specified in the data source.
     *
     * @return list of past races without results
     * @throws ServiceException if a data source access error occurs
     */
    List<Race> getRacesWithoutResults() throws ServiceException;

    /**
     * Adds horse with the specified id as a participant of the race
     * with the specified id.
     *
     * @param horseId id of the horse that is to be a participant of the race
     * @param raceId  id of the race where the horse is participating
     * @throws ServiceException if a data source access error occurs
     */
    void addHorseToRace(int horseId, int raceId) throws ServiceException;

    /**
     * Removes horse with the specified id from participants of
     * the race with the specified id.
     *
     * @param horseId id of the horse that is to be removed
     *                from race's participants
     * @param raceId  id of the race where the horse is no
     *                longer participating
     * @throws ServiceException if a data source access error occurs
     */
    void removeHorseFromRace(int horseId, int raceId) throws ServiceException;

    /**
     * Returns list of races that are happening in the future(their date
     * is after the current date).
     *
     * @return list of future races
     * @throws ServiceException if a data source access error occurs
     */
    List<Race> getUpcomingRaces() throws ServiceException;

    /**
     * Returns list of races that are happening in the future(their date
     * is after the current date) and there are no odds for the
     * horses that participate in them.
     *
     * @return list of future races without odds
     * @throws ServiceException if a data source access error occurs
     */
    List<Race> getUpcomingRacesWithoutOdds() throws ServiceException;

    /**
     * Returns list of races that happened in the past(their date
     * is before the current date).
     * <p>
     * The races are sorted in the descending order, meaning
     * the latest ones are viewed first.
     *
     * @return list of past races
     * @throws ServiceException if a data source access error occurs
     */
    List<Race> getPastRaces() throws ServiceException;

    /**
     * Returns {@code Race} object with the full information about the
     * race from the data source.
     *
     * @param raceId id of the requested race
     * @return race with the specified id
     * @throws ServiceException if a data source access error occurs
     */
    Race getRaceById(int raceId) throws ServiceException;
}
