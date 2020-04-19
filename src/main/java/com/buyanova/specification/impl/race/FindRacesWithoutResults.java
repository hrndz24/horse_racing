package com.buyanova.specification.impl.race;

import com.buyanova.specification.SqlSpecification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FindRacesWithoutResults implements SqlSpecification {

    private static final String SQL_QUERY =
            "SELECT \n" +
            "    race_id,\n" +
            "    race_prize_money,\n" +
            "    horse_winner_id,\n" +
            "    race_date,\n" +
            "    race_distance,\n" +
            "    race_location\n" +
            "FROM\n" +
            "    races\n" +
            "WHERE\n" +
            "    horse_winner_id IS NULL AND race_date< now()";

    @Override
    public PreparedStatement toSqlStatement(Connection connection) throws SQLException {
        return connection.prepareStatement(SQL_QUERY);
    }
}
