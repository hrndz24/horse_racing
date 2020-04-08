package com.buyanova.specification.impl.race;

import com.buyanova.specification.SqlSpecification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FindRacesAfterCurrentDate implements SqlSpecification {

    private static final String SQL_QUERY = "SELECT \n" +
            "    race_id,\n" +
            "    race_prize_money,\n" +
            "    horse_winner_id,\n" +
            "    race_date,\n" +
            "    race_distance\n" +
            "FROM\n" +
            "    races\n" +
            "WHERE\n" +
            "    race_date > ?";


    @Override
    public PreparedStatement toSqlStatement(Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SQL_QUERY);
        statement.setDate(1, new java.sql.Date(new java.util.Date().getTime()));
        return statement;
    }
}
