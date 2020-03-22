package buyanova.repository.user.implementation;

import buyanova.entity.User;
import buyanova.exception.RepositoryException;
import buyanova.repository.user.UserRepository;
import buyanova.specification.Specification;
import buyanova.specification.sql.SqlSpecification;

import java.util.List;

public class SqlUserRepository implements UserRepository {

    @Override
    public void add(User user) throws RepositoryException {

    }

    @Override
    public void remove(User user) throws RepositoryException {

    }

    @Override
    public void update(User user) throws RepositoryException {

    }

    @Override
    public List<User> query(Specification specification) throws RepositoryException {
        if (specification == null) {
            throw new RepositoryException("Null specification");
        }
        if (!(specification instanceof SqlSpecification)) {
            throw new RepositoryException("Specification is not based on SQL");
        }
        // TODO: 22.03.2020 finish this through connecting to database
        return null;
    }
}
