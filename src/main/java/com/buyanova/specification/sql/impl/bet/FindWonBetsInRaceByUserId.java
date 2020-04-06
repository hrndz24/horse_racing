package com.buyanova.specification.sql.impl.bet;

import com.buyanova.specification.sql.SqlSpecification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FindWonBetsInRaceByUserId implements SqlSpecification {
    private int userId;
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

    public FindWonBetsInRaceByUserId(int raceId) {
//        this.userId = userId;
        this.raceId = raceId;
    }

    @Override
    public PreparedStatement toSqlStatement(Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SQL_QUERY);
        statement.setInt(1, raceId);
        //statement.setInt(2, userId);
        return statement;
    }
}
