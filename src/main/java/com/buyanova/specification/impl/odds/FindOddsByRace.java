package com.buyanova.specification.impl.odds;

import com.buyanova.specification.SqlSpecification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FindOddsByRace implements SqlSpecification {

    private int raceId;
    private static final String SQL_QUERY =
            "SELECT \n" +
            "    odds_id,\n" +
            "    odds.race_id,\n" +
            "    bookmaker_id,\n" +
            "    horse_id,\n" +
            "    odds_in_favour,\n" +
            "    odds_against\n" +
            "FROM\n" +
            "    odds\n" +
            "        JOIN\n" +
            "    races ON odds.race_id = races.race_id\n" +
            "WHERE\n" +
            "    odds.race_id = ?";

    public FindOddsByRace(int raceId) {
        this.raceId = raceId;
    }

    @Override
    public PreparedStatement toSqlStatement(Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SQL_QUERY);
        statement.setInt(1, raceId);
        return statement;
    }
}
