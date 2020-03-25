package buyanova.repository;

import buyanova.exception.RepositoryException;
import buyanova.specification.Specification;

import java.util.List;

/**
 * {@code Repository} interface uses repository pattern to
 * manipulate data on the data access level.
 * <p>
 * Every entity that is to be stored should have an interface
 * that extends {@code Repository} interface and parameterize
 * it with its type.
 *
 * @param <T> type of entity it should provide access to.
 * @author Natalie
 * @see buyanova.repository.user.UserRepository
 * @see buyanova.repository.bet.BetRepository
 * @see buyanova.repository.horse.HorseRepository
 * @see buyanova.repository.odds.OddsRepository
 * @see buyanova.repository.race.RaceRepository
 */
public interface Repository<T> {

    /**
     * Adds an item to the specified data source.
     *
     * @param item element to be added to the data source
     * @throws RepositoryException if data source access error occurs
     */
    void add(T item) throws RepositoryException;

    /**
     * Removes an item from the specified data source.
     *
     * @param item element to be removed from the data source
     * @throws RepositoryException if data source access error occurs
     */
    void remove(T item) throws RepositoryException;

    /**
     * Updates information about {@code item} in the specified data source.
     *
     * @param item element to update information about
     * @throws RepositoryException if data source access error occurs
     */
    void update(T item) throws RepositoryException;

    /**
     * Returns a list of {@code T} items that match the criteria indicated by {@code specification}.
     *
     * @param specification instance of {@code Specification} interface that provides criteria for the query
     * @return list of items that suit the criteria of the {@code specification}
     * @throws RepositoryException if data source access error occurs
     * @see buyanova.specification.Specification
     */
    List<T> query(Specification specification) throws RepositoryException;
}
