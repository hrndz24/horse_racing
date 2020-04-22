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

    void addHorseToRace(int horseId, int raceId) throws RepositoryException;

    void removeHorseFromRace(int horseId, int raceId) throws RepositoryException;
}
