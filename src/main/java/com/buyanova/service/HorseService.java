package com.buyanova.service;

import com.buyanova.entity.Horse;
import com.buyanova.entity.Race;
import com.buyanova.exception.ServiceException;

import java.util.List;

/**
 * {@code HorseService} interface is used to describe
 * business logic concerning {@code Horse} entity.
 *
 * @author Natalie
 * @see com.buyanova.service.impl.HorseServiceImpl
 */
public interface HorseService {

    /**
     * Adds horse to the data source.
     * <p>
     * Note: the horse is marked as performing
     * by default.
     *
     * @param horse horse that is to be added
     * @throws ServiceException if null horse is passed,
     *                          if horse contains invalid fields,
     *                          if a data source access error occurs
     */
    void addHorse(Horse horse) throws ServiceException;

    /**
     * Updates information about the specified horse
     * in the data source.
     *
     * @param horse horse that is to be updated
     * @throws ServiceException if null horse is passed,
     *                          if horse contains invalid fields,
     *                          if a data source access error occurs
     */
    void updateHorse(Horse horse) throws ServiceException;

    /**
     * Removes horse from the list of performing horses.
     * <p>
     * Note: it marks the horse as non-performing
     * and in the future it can be undone.
     *
     * @param horse horse that is to be removed
     * @throws ServiceException if null horse is passed,
     *                          if a data source access error occurs
     */
    void removeHorse(Horse horse) throws ServiceException;

    /**
     * Returns list of horses that can preform in races.
     *
     * @return list of preforming horses
     * @throws ServiceException if a data source access error occurs
     */
    List<Horse> getPerformingHorses() throws ServiceException;

    /**
     * Returns list of horses that cannot perform in races.
     *
     * @return list of non-performing horses
     * @throws ServiceException if a data source access error occurs
     */
    List<Horse> getNonPerformingHorses() throws ServiceException;

    /**
     * Returns all horses present in the data source.
     *
     * @return list of all horses
     * @throws ServiceException if a data source access error occurs
     */
    List<Horse> getAllHorses() throws ServiceException;

    /**
     * Returns list of horses that participate in the specified race.
     *
     * @param race race which participants are requested
     * @return list of horses that perform in the specified race
     * @throws ServiceException if null race is passed,
     *                          if a data source access error occurs
     */
    List<Horse> getHorsesFromRace(Race race) throws ServiceException;

    /**
     * Returns {@code Horse} object with the full information about the
     * horse from the data source.
     *
     * @param horseId id of the requested horse
     * @return horse with the specified id
     * @throws ServiceException if a data source access error occurs
     */
    Horse getHorseById(int horseId) throws ServiceException;
}
