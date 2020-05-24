package com.buyanova.specification.impl.race;

import com.buyanova.specification.SqlSpecification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FindPastRacesSortedDescendingByDateWithLimitAndOffset implements SqlSpecification {

    private int offset;
    private int limit;

    public FindPastRacesSortedDescendingByDateWithLimitAndOffset(int offset, int limit) {
        this.offset = offset;
        this.limit = limit;
    }

    private static final String SQL_QUERY = "SELECT \n" +
            "    race_id,\n" +
            "    race_prize_money,\n" +
            "    horse_winner_id,\n" +
            "    race_date,\n" +
            "    race_distance,\n" +
            "    race_location\n" +
            "FROM\n" +
            "    races\n" +
            "WHERE\n" +
            "    race_date < now()\n" +
            "ORDER BY race_date DESC\n" +
            "LIMIT ? OFFSET ?";


    @Override
    public PreparedStatement toSqlStatement(Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SQL_QUERY);
        statement.setInt(1, limit);
        statement.setInt(2, offset);
        return statement;
    }
}
