package com.buyanova.repository.race;

import com.buyanova.entity.Race;
import com.buyanova.exception.RepositoryException;
import com.buyanova.repository.Repository;

/**
 * Interface used to mark classes that implement data access for {@code Race} entity.
 *
 * @author Natalie
 * @see com.buyanova.repository.Repository
 * @see com.buyanova.entity.Race
 */
public interface RaceRepository extends Repository<Race> {

    /**
     * Adds horse with the specified id as a participant of the race
     * with the specified id in the data source.
     *
     * @param horseId id of the horse that is to be a participant of the race
     * @param raceId  id of the race where the horse is participating
     * @throws RepositoryException if a data source access error occurs
     */
    void addHorseToRace(int horseId, int raceId) throws RepositoryException;

    /**
     * Removes horse with the specified id from participants of
     * the race with the specified id in the data source.
     *
     * @param horseId id of the horse that is to be removed
     *                from race's participants
     * @param raceId  id of the race where the horse is no
     *                longer participating
     * @throws RepositoryException if a data source access error occurs
     */
    void removeHorseFromRace(int horseId, int raceId) throws RepositoryException;

    /**
     * Sets horse winner of the specified race in the data source.
     * <p>
     * It automatically updates users' and bookmaker's balances
     * according to their bets and odds.
     *
     * @param race race which results are to be set
     * @throws RepositoryException if a data source access error occurs
     */
    void setRaceResults(Race race) throws RepositoryException;
}
