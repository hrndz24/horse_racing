package com.buyanova.specification.impl.user;

import com.buyanova.specification.SqlSpecification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FindUserByLogin implements SqlSpecification {

    private String login;
    private static final String SQL_QUERY = "SELECT user_id, user_name, user_login, user_password, " +
            "user_email, user_role_id, user_is_active, user_balance FROM users WHERE user_login = ?";

    public FindUserByLogin(String login) {
        this.login = login;
    }

    @Override
    public PreparedStatement toSqlStatement(Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SQL_QUERY);
        statement.setString(1, login);
        return statement;
    }
}
