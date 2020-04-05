package com.buyanova.specification.sql.impl.user;

import com.buyanova.specification.sql.SqlSpecification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FindUserById implements SqlSpecification {

    private int userId;
    private static final String SQL_QUERY = "SELECT user_id, user_name, user_login, user_password, " +
            "user_email, user_role_id, user_is_active, user_balance FROM users WHERE user_id = ?";

    public FindUserById(int userId) {
        this.userId = userId;
    }

    @Override
    public PreparedStatement toSqlStatement(Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SQL_QUERY);
        statement.setInt(1, userId);
        return statement;
    }
}
