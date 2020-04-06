package com.buyanova.specification;

import com.buyanova.specification.impl.user.FindUserByLoginAndPassword;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Specification to indicate search criteria based on sql queries.
 * <p>
 * Should be used in classes that implement {@code Repository} interface
 * and use sql database as a data source.
 *
 * @author Natalie
 * @see com.buyanova.specification.Specification
 * @see com.buyanova.repository.user.impl.SqlUserRepository#query(Specification)
 * @see FindUserByLoginAndPassword
 */
public interface SqlSpecification extends Specification {

    /**
     * Returns a sql query wrapped up in {@code PreparedStatement}.
     *
     * @param connection database connection to prepare statement on
     * @return a sql query wrapped up in {@code PreparedStatement}
     * @throws SQLException if a database access error occurs
     */
    PreparedStatement toSqlStatement(Connection connection) throws SQLException;
}
