package com.buyanova.specification.sql.impl.race;

import com.buyanova.specification.sql.SqlSpecification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;

public class FindRacesByDateRange implements SqlSpecification {

    private Date from;
    private Date to;

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
            "    race_date BETWEEN ? AND ?";

    public FindRacesByDateRange(Date from, Date to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public PreparedStatement toSqlStatement(Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SQL_QUERY);
        statement.setDate(1, from);
        statement.setDate(2, to);
        return statement;
    }
}
