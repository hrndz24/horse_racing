package com.buyanova.specification.impl.race;

import com.buyanova.specification.SqlSpecification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FindRacesAfterCurrentDateWithoutOdds implements SqlSpecification {

    private static final String SQL_QUERY = "SELECT \n" +
            "    races.race_id,\n" +
            "    race_prize_money,\n" +
            "    horse_winner_id,\n" +
            "    race_date,\n" +
            "    race_distance,\n" +
            "    race_location\n" +
            "FROM\n" +
            "    races\n" +
            "        LEFT JOIN\n" +
            "    odds ON races.race_id = odds.race_id\n" +
            "WHERE\n" +
            "    odds.race_id IS NULL\n" +
            "        AND races.race_date > NOW()";

    @Override
    public PreparedStatement toSqlStatement(Connection connection) throws SQLException {
        return connection.prepareStatement(SQL_QUERY);
    }
}
