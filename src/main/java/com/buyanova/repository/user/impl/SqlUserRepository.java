package com.buyanova.repository.user.impl;

import com.buyanova.entity.User;
import com.buyanova.entity.UserRole;
import com.buyanova.exception.RepositoryException;
import com.buyanova.pool.ConnectionPool;
import com.buyanova.pool.ProxyConnection;
import com.buyanova.repository.ColumnLabel;
import com.buyanova.repository.user.UserRepository;
import com.buyanova.specification.Specification;
import com.buyanova.specification.SqlSpecification;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of {@code UserRepository} interface
 * that uses sql database as a data source.
 *
 * @author Natalie
 * @see com.buyanova.repository.user.UserRepository
 */
public enum SqlUserRepository implements UserRepository {

    INSTANCE;

    private static final String INSERT_QUERY = "INSERT INTO users(user_name, user_login, user_password," +
            "user_email, user_role_id, user_is_active, user_balance) VALUES(?,?,?,?,?,?,?)";

    private static final String REMOVE_QUERY = "UPDATE users SET user_is_active = false WHERE user_id = ?";

    private static final String UPDATE_QUERY = "UPDATE users SET user_name = ?, user_login = ?," +
            "user_password = ?, user_email = ?, user_role_id = ?, user_balance = ? WHERE user_id = ?";

    private static final String UNBLOCK_QUERY = "UPDATE users SET user_is_active = true WHERE user_id = ?";

    private static final String GET_ROWS_COUNT = "SELECT COUNT(*) FROM users";

    @Override
    public void add(User user) throws RepositoryException {
        try (ProxyConnection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, user.getName());
            statement.setString(2, user.getLogin());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getEmail());
            statement.setInt(5, user.getUserRole().getId());
            statement.setBoolean(6, user.isActive());
            statement.setBigDecimal(7, user.getBalance());
            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Failed to add user", e);
        }
    }

    @Override
    public void remove(User user) throws RepositoryException {
        try (ProxyConnection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(REMOVE_QUERY)) {

            statement.setInt(1, user.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException("Failed to remove user", e);
        }
    }

    @Override
    public void update(User user) throws RepositoryException {
        try (ProxyConnection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {

            statement.setString(1, user.getName());
            statement.setString(2, user.getLogin());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getEmail());
            statement.setInt(5, user.getUserRole().getId());
            statement.setBigDecimal(6, user.getBalance());
            statement.setInt(7, user.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException("Failed to update user", e);
        }
    }

    @Override
    public void unblock(User user) throws RepositoryException {
        try (ProxyConnection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(UNBLOCK_QUERY)) {

            statement.setInt(1, user.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException("Failed to unblock user", e);
        }
    }

    @Override
    public int getNumberOfRecords() throws RepositoryException {
        try (ProxyConnection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ROWS_COUNT);
             ResultSet resultSet = statement.executeQuery()) {

            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            throw new RepositoryException("Failed to get number of records", e);
        }
    }

    @Override
    public List<User> query(Specification specification) throws RepositoryException {
        if (specification == null) {
            throw new RepositoryException("Null specification");
        }
        if (!(specification instanceof SqlSpecification)) {
            throw new RepositoryException("Invalid specification type");
        }
        SqlSpecification sqlSpecification = ((SqlSpecification) specification);
        return getUsersFromDatabase(sqlSpecification);
    }

    private List<User> getUsersFromDatabase(SqlSpecification specification) throws RepositoryException {
        List<User> users = new ArrayList<>();
        try (ProxyConnection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = specification.toSqlStatement(connection);
             ResultSet resultSet = statement.executeQuery()) {

            User user;
            while (resultSet.next()) {
                user = buildUser(resultSet);
                users.add(user);
            }
        } catch (SQLException e) {
            throw new RepositoryException("Failed to get users", e);
        }
        return users;
    }

    private User buildUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt(ColumnLabel.USER_ID.getValue()));
        user.setName(resultSet.getString(ColumnLabel.USER_NAME.getValue()));
        user.setLogin(resultSet.getString(ColumnLabel.USER_LOGIN.getValue()));
        user.setPassword(resultSet.getString(ColumnLabel.USER_PASSWORD.getValue()));
        user.setEmail(resultSet.getString(ColumnLabel.USER_EMAIL.getValue()));
        user.setUserRole(UserRole.getById(resultSet.getInt(ColumnLabel.USER_ROLE_ID.getValue())));
        user.setActive(resultSet.getBoolean(ColumnLabel.USER_IS_ACTIVE.getValue()));
        user.setBalance(resultSet.getBigDecimal(ColumnLabel.USER_BALANCE.getValue()));
        return user;
    }
}
