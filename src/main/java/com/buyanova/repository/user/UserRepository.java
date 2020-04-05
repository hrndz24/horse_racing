package com.buyanova.repository.user;

import com.buyanova.entity.User;
import com.buyanova.repository.Repository;

/**
 * Interface used to mark classes that implement data access for {@code User} entity.
 *
 * @see com.buyanova.repository.Repository
 * @see com.buyanova.entity.User
 * @author Natalie
 */
public interface UserRepository extends Repository<User> {

}
