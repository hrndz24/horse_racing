package com.buyanova.specification.impl.horse;

import com.buyanova.specification.SqlSpecification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FindHorsesPerformingInRace implements SqlSpecification {

    private int raceId;

    private static final String SQL_QUERY =
            "SELECT \n" +
            "    horses.horse_id,\n" +
            "    jockey_id,\n" +
            "    horse_name, \n" +
            "    horse_breed,\n" +
            "    horse_age,\n" +
            "    is_performing,\n" +
            "    races_won_number,\n" +
            "    races_lost_number\n" +
            "FROM\n" +
            "    horses\n" +
            "        JOIN\n" +
            "    race_horses ON horses.horse_id = race_horses.horse_id\n" +
            "WHERE\n" +
            "    race_id = ?;";

    public FindHorsesPerformingInRace(int raceId) {
        this.raceId = raceId;
    }

    @Override
    public PreparedStatement toSqlStatement(Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SQL_QUERY);
        statement.setInt(1, raceId);
        return statement;
    }
}
