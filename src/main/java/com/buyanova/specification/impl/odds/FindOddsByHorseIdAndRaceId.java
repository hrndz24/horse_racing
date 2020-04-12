package com.buyanova.specification.impl.odds;

import com.buyanova.specification.SqlSpecification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FindOddsByHorseIdAndRaceId implements SqlSpecification {

    private int horseId;
    private int raceId;

    private static final String SQL_QUERY = "SELECT \n" +
            "    odds_id,\n" +
            "    race_id,\n" +
            "    bookmaker_id,\n" +
            "    horse_id,\n" +
            "    odds_in_favour,\n" +
            "    odds_against\n" +
            "FROM\n" +
            "    odds\n" +
            "WHERE\n" +
            "    race_id = ? AND horse_id = ?";

    public FindOddsByHorseIdAndRaceId(int horseId, int raceId) {
        this.horseId = horseId;
        this.raceId = raceId;
    }

    @Override
    public PreparedStatement toSqlStatement(Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SQL_QUERY);
        statement.setInt(1, raceId);
        statement.setInt(2, horseId);
        return statement;
    }
}
