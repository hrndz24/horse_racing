package com.buyanova.repository.race;

import com.buyanova.entity.Horse;
import com.buyanova.entity.Race;
import com.buyanova.exception.RepositoryException;
import com.buyanova.repository.Repository;

/**
 * Interface used to mark classes that implement data access for {@code Race} entity.
 *
 * @see com.buyanova.repository.Repository
 * @see com.buyanova.entity.Race
 * @author Natalie
 */
public interface RaceRepository extends Repository<Race> {

    // TODO: 11.04.2020 javadoc
    void addHorseToRace(Horse horse, int raceId) throws RepositoryException;
}
