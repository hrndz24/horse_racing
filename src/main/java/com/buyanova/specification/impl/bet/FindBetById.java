package com.buyanova.specification.impl.bet;

import com.buyanova.specification.SqlSpecification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FindBetById implements SqlSpecification {

    private int betId;
    private static final String SQL_QUERY = "SELECT bet_id, bet_sum, user_id, odds_id " +
            "FROM bets WHERE bet_id = ?";

    public FindBetById(int betId) {
        this.betId = betId;
    }

    @Override
    public PreparedStatement toSqlStatement(Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SQL_QUERY);
        statement.setInt(1, betId);
        return statement;
    }
}
