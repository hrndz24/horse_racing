package com.buyanova.repository.user;

import com.buyanova.entity.User;
import com.buyanova.exception.RepositoryException;
import com.buyanova.repository.Repository;

/**
 * Interface used to mark classes that implement data access for {@code User} entity.
 *
 * @author Natalie
 * @see com.buyanova.repository.Repository
 * @see com.buyanova.entity.User
 */
public interface UserRepository extends Repository<User> {

    /**
     * Marks user as active in the specified data source.
     *
     * @param user user that is to be unblocked in the data source.
     * @throws RepositoryException if a data source access error occurs
     */
    void unblock(User user) throws RepositoryException;

    /**
     * Returns number of records in the data source for {@code User} entity.
     *
     * @return number of records in the data source for {@code User} entity
     * @throws RepositoryException if a data source access error occurs
     */
    int getNumberOfRecords() throws RepositoryException;
}
