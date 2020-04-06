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
 * @see com.buyanova.repository.Repository
 * @author Natalie
 */
public enum RepositoryFactory {

    INSTANCE;

    private UserRepository userRepository = SqlUserRepository.INSTANCE;
    private HorseRepository horseRepository = SqlHorseRepository.INSTANCE;
    private RaceRepository raceRepository = SqlRaceRepository.INSTANCE;
    private BetRepository betRepository = SqlBetRepository.INSTANCE;
    private OddsRepository oddsRepository = SqlOddsRepository.INSTANCE;

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public HorseRepository getHorseRepository() {
        return horseRepository;
    }

    public RaceRepository getRaceRepository() {
        return raceRepository;
    }

    public BetRepository getBetRepository() {
        return betRepository;
    }

    public OddsRepository getOddsRepository() {
        return oddsRepository;
    }
}
