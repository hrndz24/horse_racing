package com.buyanova.repository.odds.impl;

import com.buyanova.entity.Odds;
import com.buyanova.exception.RepositoryException;
import com.buyanova.pool.ConnectionPool;
import com.buyanova.repository.ColumnLabel;
import com.buyanova.repository.odds.OddsRepository;
import com.buyanova.specification.Specification;
import com.buyanova.specification.SqlSpecification;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of {@code OddsRepository} interface
 * that uses sql database as a data source.
 *
 * @author Natalie
 * @see com.buyanova.repository.odds.OddsRepository
 */

public enum SqlOddsRepository implements OddsRepository {

    INSTANCE;

    private static final String INSERT_QUERY = "INSERT INTO odds(race_id, bookmaker_id," +
            "horse_id, odds_in_favour, odds_against) VALUES(?,?,?,?,?)";

    private static final String REMOVE_QUERY = "DELETE FROM odds WHERE odds_id = ?";

    private static final String UPDATE_QUERY = "UPDATE odds SET race_id = ?, bookmaker_id = ?," +
            "horse_id = ?, odds_in_favour = ?, odds_against = ? WHERE odds_id = ?";

    @Override
    public void add(Odds odds) throws RepositoryException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS)) {

            statement.setInt(1, odds.getRaceId());
            statement.setInt(2, odds.getBookmakerId());
            statement.setInt(3, odds.getHorseId());
            statement.setInt(4, odds.getOddsInFavour());
            statement.setInt(5, odds.getOddsAgainst());
            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    odds.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Failed to add odds", e);
        }
    }

    @Override
    public void remove(Odds odds) throws RepositoryException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(REMOVE_QUERY)) {

            statement.setInt(1, odds.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException("Failed to remove odds", e);
        }
    }

    @Override
    public void update(Odds odds) throws RepositoryException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {

            statement.setInt(1, odds.getRaceId());
            statement.setInt(2, odds.getBookmakerId());
            statement.setInt(3, odds.getHorseId());
            statement.setInt(4, odds.getOddsInFavour());
            statement.setInt(5, odds.getOddsAgainst());
            statement.setInt(6, odds.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException("Failed to update odds", e);
        }
    }

    @Override
    public List<Odds> query(Specification specification) throws RepositoryException {
        if (specification == null) {
            throw new RepositoryException("Null specification");
        }
        if (!(specification instanceof SqlSpecification)) {
            throw new RepositoryException("Invalid specification type");
        }
        SqlSpecification sqlSpecification = (SqlSpecification) specification;
        return getOddsFromDatabase(sqlSpecification);
    }

    private List<Odds> getOddsFromDatabase(SqlSpecification specification) throws RepositoryException {
        List<Odds> oddsList = new ArrayList<>();
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = specification.toSqlStatement(connection);
             ResultSet resultSet = statement.executeQuery()) {

            Odds odds;
            while (resultSet.next()) {
                odds = buildOdds(resultSet);
                oddsList.add(odds);
            }
        } catch (SQLException e) {
            throw new RepositoryException("Failed to get odds", e);
        }
        return oddsList;
    }

    private Odds buildOdds(ResultSet resultSet) throws SQLException {
        Odds odds = new Odds();
        odds.setId(resultSet.getInt(ColumnLabel.ODDS_ID.getValue()));
        odds.setRaceId(resultSet.getInt(ColumnLabel.RACE_ID.getValue()));
        odds.setBookmakerId(resultSet.getInt(ColumnLabel.BOOKMAKER_ID.getValue()));
        odds.setHorseId(resultSet.getInt(ColumnLabel.HORSE_ID.getValue()));
        odds.setOddsInFavour(resultSet.getInt(ColumnLabel.ODDS_IN_FAVOUR.getValue()));
        odds.setOddsAgainst(resultSet.getInt(ColumnLabel.ODDS_AGAINST.getValue()));
        return odds;
    }
}
