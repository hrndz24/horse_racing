package com.buyanova.service;

import com.buyanova.entity.Odds;
import com.buyanova.exception.ServiceException;

import java.util.List;

/**
 * {@code OddsService} interface is used to describe
 * business logic concerning {@code Odds} entity.
 *
 * @author Natalie
 * @see com.buyanova.service.impl.OddsServiceImpl
 */
public interface OddsService {

    /**
     * Adds odds to the data source.
     * <p>
     * Note: odds can be added only by bookmaker
     * and only to the future races.
     *
     * @param odds odds that are to be added
     * @throws ServiceException if null odds are passed,
     *                          if odds contain invalid fields
     *                          if a data source access error occurs
     */
    void addOdds(Odds odds) throws ServiceException;

    /**
     * Updates information about the numbers of specified
     * odds in the data source.
     *
     * @param odds odds that are to be updated
     * @throws ServiceException if null odds are passed,
     *                          if odds contain non-positive numbers,
     *                          if a data source access error occurs
     */
    void updateOdds(Odds odds) throws ServiceException;

    /**
     * Returns list of odds for horses that participate in
     * the race with specified id.
     *
     * @param raceId id of the race which odds are requested
     * @return list of odds for the specified race
     * @throws ServiceException if a data source access error occurs
     */
    List<Odds> getOddsForRace(int raceId) throws ServiceException;

    /**
     * Returns odds for one horse that participates in the specified race.
     *
     * @param horseId id of the horse which odds are requested
     * @param raceId  id of the race where the horse participates
     * @return odds for the specified horse in a specified race
     * @throws ServiceException if a data source access error occurs
     */
    Odds getOddsForHorseInRace(int horseId, int raceId) throws ServiceException;

    /**
     * Returns {@code Odds} object with the full information about the
     * odds from the data source.
     *
     * @param oddsId id of the requested odds
     * @return odds with the specified id
     * @throws ServiceException if odds with such id don't exist
     *                          if a data source access error occurs
     */
    Odds getOddsById(int oddsId) throws ServiceException;
}
