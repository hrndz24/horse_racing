package com.buyanova.specification.sql.impl.race;

import com.buyanova.specification.sql.SqlSpecification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FindRaceById implements SqlSpecification {

    private int raceId;
    private static final String SQL_QUERY =
                    "SELECT \n" +
                    "    race_id,\n" +
                    "    race_prize_money,\n" +
                    "    horse_winner_id,\n" +
                    "    race_date,\n" +
                    "    race_distance\n" +
                    "FROM\n" +
                    "    races\n" +
                    "WHERE\n" +
                    "    race_id = ?";

    public FindRaceById(int raceId) {
        this.raceId = raceId;
    }

    @Override
    public PreparedStatement toSqlStatement(Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SQL_QUERY);
        statement.setInt(1, raceId);
        return statement;
    }
}
