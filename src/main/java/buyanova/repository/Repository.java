package buyanova.repository;

import buyanova.exception.RepositoryException;
import buyanova.specification.Specification;

import java.util.List;

public interface Repository<T> {

    void add(T item) throws RepositoryException;

    void remove(T item) throws RepositoryException;

    void update(T item) throws RepositoryException;

    List<T> query(Specification specification) throws RepositoryException;
}
