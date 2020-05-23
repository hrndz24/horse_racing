package com.buyanova.factory;

import com.buyanova.repository.bet.BetRepository;
import com.buyanova.repository.bet.impl.SqlBetRepository;
import com.buyanova.repository.horse.HorseRepository;
import com.buyanova.repository.horse.impl.SqlHorseRepository;
import com.buyanova.repository.odds.OddsRepository;
import com.buyanova.repository.odds.impl.SqlOddsRepository;
import com.buyanova.repository.race.RaceRepository;
import com.buyanova.repository.race.impl.SqlRaceRepository;
import com.buyanova.repository.user.UserRepository;
import com.buyanova.repository.user.impl.SqlUserRepository;

/**
 * {@code RepositoryFactory} class uses factory pattern to decide
 * what {@code Repository} implementations it should provide to
 * layers above the data access layer.
 * <p>
 * Currently it provides classes that use sql database as data source.
 *
 * @author Natalie
 * @see com.buyanova.repository.Repository
 */
public enum RepositoryFactory {

    INSTANCE;

    public UserRepository getUserRepository() {
        return SqlUserRepository.INSTANCE;
    }

    public HorseRepository getHorseRepository() {
        return SqlHorseRepository.INSTANCE;
    }

    public RaceRepository getRaceRepository() {
        return SqlRaceRepository.INSTANCE;
    }

    public BetRepository getBetRepository() {
        return SqlBetRepository.INSTANCE;
    }

    public OddsRepository getOddsRepository() {
        return SqlOddsRepository.INSTANCE;
    }
}
