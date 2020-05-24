package com.buyanova.specification.impl.user;

import com.buyanova.specification.SqlSpecification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FindUsersWhoseLoginStartsWithPattern implements SqlSpecification {

    private String pattern;

    public FindUsersWhoseLoginStartsWithPattern(String pattern) {
        this.pattern = pattern;
    }

    private static final String SQL_QUERY =
            "SELECT \n" +
                    "    user_id,\n" +
                    "    user_name,\n" +
                    "    user_login,\n" +
                    "    user_password,\n" +
                    "    user_email,\n" +
                    "    user_role_id,\n" +
                    "    user_is_active,\n" +
                    "    user_balance\n" +
                    "FROM\n" +
                    "    users\n" +
                    "WHERE\n" +
                    "    user_login LIKE ?";

    @Override
    public PreparedStatement toSqlStatement(Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SQL_QUERY);
        statement.setString(1, pattern + "%");
        return statement;
    }
}
