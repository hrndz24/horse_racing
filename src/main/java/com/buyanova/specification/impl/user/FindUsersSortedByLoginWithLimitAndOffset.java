package com.buyanova.specification.impl.user;

import com.buyanova.specification.SqlSpecification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FindUsersSortedByLoginWithLimitAndOffset implements SqlSpecification {

    private int limit;
    private int offset;
    private static final String SQL_QUERY = "SELECT \n" +
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
            "ORDER BY user_login\n" +
            "LIMIT ? OFFSET ?";

    public FindUsersSortedByLoginWithLimitAndOffset(int offset, int limit) {
        this.limit = limit;
        this.offset = offset;
    }

    @Override
    public PreparedStatement toSqlStatement(Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SQL_QUERY);
        statement.setInt(1, limit);
        statement.setInt(2, offset);
        return statement;
    }
}
