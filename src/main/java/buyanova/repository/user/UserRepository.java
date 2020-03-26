package buyanova.repository.user;

import buyanova.entity.User;
import buyanova.repository.Repository;

/**
 * Interface used to mark classes that implement data access for {@code User} entity.
 *
 * @see buyanova.repository.Repository
 * @see buyanova.entity.User
 * @author Natalie
 */
public interface UserRepository extends Repository<User> {

}
