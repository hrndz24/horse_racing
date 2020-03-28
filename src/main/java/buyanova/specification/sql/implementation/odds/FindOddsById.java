package buyanova.specification.sql.implementation.odds;

import buyanova.specification.sql.SqlSpecification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FindOddsById implements SqlSpecification {

    private int oddsId;
    private static final String SQL_QUERY = "SELECT odds_id, bookmaker_id, race_id, horse_id," +
            "odds_in_favour, odds_against FROM odds WHERE odds_id = ?";

    public FindOddsById(int oddsId) {
        this.oddsId = oddsId;
    }

    @Override
    public PreparedStatement toSqlStatement(Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SQL_QUERY);
        statement.setInt(1, oddsId);
        return statement;
    }
}
