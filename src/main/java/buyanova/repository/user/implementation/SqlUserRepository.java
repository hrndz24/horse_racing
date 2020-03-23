package buyanova.repository.user.implementation;

import buyanova.entity.User;
import buyanova.entity.UserRole;
import buyanova.exception.RepositoryException;
import buyanova.pool.ConnectionPool;
import buyanova.pool.ProxyConnection;
import buyanova.repository.ColumnLabel;
import buyanova.repository.user.UserRepository;
import buyanova.specification.Specification;
import buyanova.specification.sql.SqlSpecification;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public enum SqlUserRepository implements UserRepository {

    INSTANCE;

    private static final String INSERT_QUERY = "INSERT INTO users(user_name, user_login, user_password," +
            "user_email, user_role_id, is_active, user_balance) VALUES(?,?,?,?,?,?,?)";

    private static final String REMOVE_QUERY = "UPDATE users SET is_active = false WHERE user_id = ?";

    private static final String UPDATE_QUERY = "UPDATE users SET user_name = ?, user_login = ?," +
            "user_password = ?, user_email = ?, user_role_id = ?, user_balance = ? WHERE user_id = ?";

    @Override
    public void add(User user) throws RepositoryException {
        try (ProxyConnection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_QUERY)) {

            statement.setString(1, user.getName());
            statement.setString(2, user.getLogin());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getEmail());
            statement.setInt(5, user.getUserRole().getId());
            statement.setBoolean(6, user.isActive());
            statement.setBigDecimal(7, user.getBalance());

            statement.executeUpdate();
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
    public List<User> query(Specification specification) throws RepositoryException {
        if (specification == null) {
            throw new RepositoryException("Null specification");
        }
        if (!(specification instanceof SqlSpecification)) {
            throw new RepositoryException("Invalid specification type");
        }

        List<User> users = new ArrayList<>();
        SqlSpecification sqlSpecification = ((SqlSpecification) specification);

        try (ProxyConnection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = sqlSpecification.toSqlStatement(connection);
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
        user.setActive(resultSet.getBoolean(ColumnLabel.IS_ACTIVE.getValue()));
        user.setBalance(resultSet.getBigDecimal(ColumnLabel.USER_BALANCE.getValue()));
        return user;
    }
}
