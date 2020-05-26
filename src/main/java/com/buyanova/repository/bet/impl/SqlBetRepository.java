package com.buyanova.repository.bet.impl;

import com.buyanova.entity.Bet;
import com.buyanova.exception.RepositoryException;
import com.buyanova.pool.ConnectionPool;
import com.buyanova.repository.ColumnLabel;
import com.buyanova.repository.bet.BetRepository;
import com.buyanova.specification.Specification;
import com.buyanova.specification.SqlSpecification;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of {@code BetRepository} interface
 * that uses sql database as a data source.
 *
 * @author Natalie
 * @see com.buyanova.repository.bet.BetRepository
 */
public enum SqlBetRepository implements BetRepository {

    INSTANCE;

    private static final String MAKE_BET = "{CALL usp_MakeBet(?, ?, ?, ?)}";

    private static final String REMOVE_BET = "{CALL usp_DeleteBet(?, ?)}";

    private static final String UPDATE_BET_SUM = "{CALL usp_UpdateBetSum(?, ?, ?)}";

    @Override
    public void add(Bet bet) throws RepositoryException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             CallableStatement statement = connection.prepareCall(MAKE_BET)) {

            statement.setInt(1, bet.getUserId());
            statement.setBigDecimal(2, bet.getSum());
            statement.setInt(3, bet.getOddsId());
            statement.registerOutParameter(4, Types.INTEGER);
            statement.execute();
            int betId = statement.getInt(4);
            bet.setId(betId);
        } catch (SQLException e) {
            throw new RepositoryException("Failed to add bet", e);
        }
    }

    @Override
    public void remove(Bet bet) throws RepositoryException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             CallableStatement statement = connection.prepareCall(REMOVE_BET)) {

            statement.setInt(1, bet.getId());
            statement.setInt(2, bet.getUserId());
            statement.execute();
        } catch (SQLException e) {
            throw new RepositoryException("Failed to remove bet", e);
        }
    }

    @Override
    public void update(Bet bet) throws RepositoryException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             CallableStatement statement = connection.prepareCall(UPDATE_BET_SUM)) {

            statement.setInt(1, bet.getId());
            statement.setBigDecimal(2, bet.getSum());
            statement.setInt(3, bet.getUserId());

            statement.execute();
        } catch (SQLException e) {
            throw new RepositoryException("Failed to update bet", e);
        }
    }

    @Override
    public List<Bet> query(Specification specification) throws RepositoryException {
        if (specification == null) {
            throw new RepositoryException("Null specification");
        }
        if (!(specification instanceof SqlSpecification)) {
            throw new RepositoryException("Invalid specification type");
        }
        SqlSpecification sqlSpecification = ((SqlSpecification) specification);
        return getBetsFromDatabase(sqlSpecification);
    }

    private List<Bet> getBetsFromDatabase(SqlSpecification specification) throws RepositoryException {
        List<Bet> bets = new ArrayList<>();
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = specification.toSqlStatement(connection);
             ResultSet resultSet = statement.executeQuery()) {

            Bet bet;
            while (resultSet.next()) {
                bet = buildBet(resultSet);
                bets.add(bet);
            }
        } catch (SQLException e) {
            throw new RepositoryException("Failed to get bets", e);
        }
        return bets;
    }

    private Bet buildBet(ResultSet resultSet) throws SQLException {
        Bet bet = new Bet();
        bet.setId(resultSet.getInt(ColumnLabel.BET_ID.getValue()));
        bet.setUserId(resultSet.getInt(ColumnLabel.USER_ID.getValue()));
        bet.setSum(resultSet.getBigDecimal(ColumnLabel.BET_SUM.getValue()));
        bet.setOddsId(resultSet.getInt(ColumnLabel.ODDS_ID.getValue()));
        return bet;
    }
}
