package com.buyanova.specification.impl.user;

import com.buyanova.specification.SqlSpecification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FindUsersWhoseBetsWonInRace implements SqlSpecification {

    private int raceId;

    private static final String SQL_QUERY =
                    "SELECT \n" +
                    "    users.user_id,\n" +
                    "    user_name,\n" +
                    "    user_login,\n" +
                    "    user_password,\n" +
                    "    user_email,\n" +
                    "    user_role_id,\n" +
                    "    user_is_active,\n" +
                    "    user_balance\n" +
                    "FROM\n" +
                    "    users\n" +
                    "        JOIN\n" +
                    "    bets ON users.user_id = bets.user_id\n" +
                    "        JOIN\n" +
                    "    odds ON odds.odds_id = bets.odds_id\n" +
                    "        JOIN\n" +
                    "    races ON odds.race_id = races.race_id\n" +
                    "WHERE\n" +
                    "    odds.horse_id = races.horse_winner_id\n" +
                    "        AND races.race_id = ?;";

    public FindUsersWhoseBetsWonInRace(int raceId) {
        this.raceId = raceId;
    }

    @Override
    public PreparedStatement toSqlStatement(Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SQL_QUERY);
        statement.setInt(1, raceId);
        return statement;
    }
}
