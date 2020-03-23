package buyanova.specification.sql.implementation.user;

import buyanova.specification.sql.SqlSpecification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FindUserByLoginAndPassword implements SqlSpecification {

    private String login;
    private String password;

    private static final String SQL_QUERY = "SELECT user_id, user_name, user_login, user_password, " +
            "user_email, user_role_id, is_active, user_balance FROM users WHERE user_login = ? and user_password = ?";

    public FindUserByLoginAndPassword(String login, String password) {
        this.login = login;
        this.password = password;
    }

    @Override
    public PreparedStatement toSqlStatement(Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SQL_QUERY);
        statement.setString(1, login);
        statement.setString(2, password);
        return statement;
    }
}
