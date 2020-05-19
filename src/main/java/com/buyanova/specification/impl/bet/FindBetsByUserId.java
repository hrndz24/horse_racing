package com.buyanova.specification.impl.bet;

import com.buyanova.specification.SqlSpecification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FindBetsByUserId implements SqlSpecification {

    private int userId;

    private static final String SQL_QUERY =
            "SELECT \n" +
                    "    bet_id,\n" +
                    "    bets.user_id,\n" +
                    "    bet_sum,\n" +
                    "    odds_id\n" +
                    "FROM\n" +
                    "    bets\n" +
                    "        JOIN\n" +
                    "    users ON bets.user_id = users.user_id\n" +
                    "WHERE\n" +
                    "    bets.user_id = ?";

    public FindBetsByUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public PreparedStatement toSqlStatement(Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SQL_QUERY);
        statement.setInt(1, userId);
        return statement;
    }
}
