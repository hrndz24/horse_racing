package buyanova.factory;

import buyanova.repository.bet.BetRepository;
import buyanova.repository.bet.implementation.SqlBetRepository;
import buyanova.repository.horse.HorseRepository;
import buyanova.repository.horse.implementation.SqlHorseRepository;
import buyanova.repository.odds.OddsRepository;
import buyanova.repository.odds.implementation.SqlOddsRepository;
import buyanova.repository.race.RaceRepository;
import buyanova.repository.race.implementation.SqlRaceRepository;
import buyanova.repository.user.UserRepository;
import buyanova.repository.user.implementation.SqlUserRepository;

/**
 * {@code RepositoryFactory} class uses factory pattern to decide
 * what {@code Repository} implementations it should provide to layers above
 * the data access layer.
 * <p>
 * Currently it provides classes that use sql database as data source.
 *
 * @see buyanova.repository.Repository
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
