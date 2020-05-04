package com.buyanova.specification.impl.bet;

import com.buyanova.specification.SqlSpecification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FindWonBetsInRace implements SqlSpecification {
    private int raceId;
    private static final String SQL_QUERY =
                    "SELECT \n" +
                    "    bets.bet_id, bet_sum, bets.user_id, bets.odds_id\n" +
                    "FROM\n" +
                    "    bets\n" +
                    "        JOIN\n" +
                    "    odds ON bets.odds_id = odds.odds_id\n" +
                    "        JOIN\n" +
                    "    races ON odds.race_id = races.race_id\n" +
                    "WHERE\n" +
                    "    odds.horse_id = races.horse_winner_id\n" +
                    "        AND races.race_id = ?";

    public FindWonBetsInRace(int raceId) {
        this.raceId = raceId;
    }

    @Override
    public PreparedStatement toSqlStatement(Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SQL_QUERY);
        statement.setInt(1, raceId);
        return statement;
    }
}
