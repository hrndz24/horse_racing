package com.buyanova.repository.race.impl;

import com.buyanova.entity.Race;
import com.buyanova.exception.RepositoryException;
import com.buyanova.pool.ConnectionPool;
import com.buyanova.pool.ProxyConnection;
import com.buyanova.repository.ColumnLabel;
import com.buyanova.repository.race.RaceRepository;
import com.buyanova.specification.Specification;
import com.buyanova.specification.SqlSpecification;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Implementation of {@code RaceRepository} interface
 * that uses sql database as a data source.
 *
 * @author Natalie
 * @see com.buyanova.repository.race.RaceRepository
 */
public enum SqlRaceRepository implements RaceRepository {

    INSTANCE;

    private static final String INSERT_QUERY = "INSERT INTO races(race_prize_money," +
            "race_date, race_distance, race_location) VALUES(?,?,?,?)";

    private static final String REMOVE_QUERY = "DELETE FROM races WHERE race_id = ?";

    private static final String UPDATE_QUERY = "UPDATE races SET race_prize_money = ?," +
            "race_date = ?, race_location = ?, race_distance = ? WHERE race_id = ?";

    private static final String ADD_HORSE_TO_RACE_QUERY = "INSERT INTO race_horses" +
            "(race_id, horse_id) VALUES(?, ?)";

    private static final String REMOVE_HORSE_FROM_RACE_QUERY = "DELETE FROM race_horses " +
            "WHERE horse_id = ? AND race_id = ?";

    private static final String SET_HORSE_WINNER = "UPDATE races SET horse_winner_id = ? WHERE race_id = ?";

    private static final String SELECT_WON_BETS = "SELECT bet_sum, bets.user_id, user_balance, odds.bookmaker_id,\n" +
            "    odds_in_favour FROM users JOIN bets ON users.user_id = bets.user_id\n" +
            "    JOIN odds ON bets.odds_id = odds.odds_id JOIN races ON odds.race_id = races.race_id\n" +
            "WHERE odds.horse_id = races.horse_winner_id AND races.race_id = ?";

    private static final String SELECT_LOST_BETS = "SELECT bet_sum, bets.user_id, user_balance, odds.bookmaker_id,\n" +
            "    odds_against FROM users JOIN bets ON users.user_id = bets.user_id JOIN\n" +
            "    odds ON bets.odds_id = odds.odds_id JOIN races ON odds.race_id = races.race_id\n" +
            "WHERE odds.horse_id != races.horse_winner_id AND races.race_id = ?";

    private static final String UPDATE_USER_BALANCE = "UPDATE users SET user_balance = ? WHERE user_id = ?";

    private static final String SELECT_BOOKMAKER_BALANCE = "SELECT user_balance FROM users WHERE user_id=?";


    @Override
    public void addHorseToRace(int horseId, int raceId) throws RepositoryException {
        try (ProxyConnection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(ADD_HORSE_TO_RACE_QUERY)) {

            statement.setInt(1, raceId);
            statement.setInt(2, horseId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException("Failed to add horse to the race", e);
        }
    }

    @Override
    public void removeHorseFromRace(int horseId, int raceId) throws RepositoryException {
        try (ProxyConnection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(REMOVE_HORSE_FROM_RACE_QUERY)) {

            statement.setInt(1, horseId);
            statement.setInt(2, raceId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException("Failed to remove horse from the race", e);
        }
    }

    @Override
    public void add(Race race) throws RepositoryException {
        try (ProxyConnection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS)) {

            statement.setBigDecimal(1, race.getPrizeMoney());
            statement.setTimestamp(2, new Timestamp(race.getDate().getTime()));
            statement.setInt(3, race.getDistance());
            statement.setString(4, race.getLocation());
            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    race.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Failed to add race", e);
        }
    }

    @Override
    public void remove(Race race) throws RepositoryException {
        try (ProxyConnection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(REMOVE_QUERY)) {

            statement.setInt(1, race.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException("Failed to remove race", e);
        }
    }

    @Override
    public void update(Race race) throws RepositoryException {
        try (ProxyConnection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {

            statement.setBigDecimal(1, race.getPrizeMoney());
            statement.setTimestamp(2, new Timestamp(race.getDate().getTime()));
            statement.setString(3, race.getLocation());
            statement.setInt(4, race.getDistance());
            statement.setInt(5, race.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException("Failed to update race", e);
        }
    }

    @Override
    public void setRaceResults(Race race) throws RepositoryException {
        ProxyConnection connection = ConnectionPool.INSTANCE.getConnection();
        try {
            connection.setAutoCommit(false);
            setHorseWinner(connection, race);
            payOutWonBets(connection, race.getId());
            withdrawLostBets(connection, race.getId());
            connection.commit();
        } catch (SQLException e) {
            tryToRollbackTransaction(connection);
            throw new RepositoryException(e);
        } finally {
            connection.close();
        }
    }

    private void setHorseWinner(Connection connection, Race race) throws SQLException {
        try (PreparedStatement setHorseWinnerStatement = connection.prepareStatement(SET_HORSE_WINNER)) {
            setHorseWinnerStatement.setInt(1, race.getHorseWinnerId());
            setHorseWinnerStatement.setInt(2, race.getId());
            setHorseWinnerStatement.executeUpdate();
        }
    }

    private void payOutWonBets(Connection connection, int raceId) throws SQLException {
        try (PreparedStatement selectWonBetsStatement = connection.prepareStatement(SELECT_WON_BETS)) {
            selectWonBetsStatement.setInt(1, raceId);

            try (ResultSet wonBets = selectWonBetsStatement.executeQuery()) {
                while (wonBets.next()) {
                    payOutAWonBet(connection, wonBets);
                }
            }
        }
    }

    private void payOutAWonBet(Connection connection, ResultSet wonBet) throws SQLException {
        BigDecimal sum = wonBet.getBigDecimal(ColumnLabel.BET_SUM.getValue());
        int userId = wonBet.getInt(ColumnLabel.USER_ID.getValue());
        BigDecimal balance = wonBet.getBigDecimal(ColumnLabel.USER_BALANCE.getValue());
        int bookmakerId = wonBet.getInt(ColumnLabel.BOOKMAKER_ID.getValue());
        int oddsInFavour = wonBet.getInt(ColumnLabel.ODDS_IN_FAVOUR.getValue());
        BigDecimal wonSum = sum.multiply(new BigDecimal(oddsInFavour));

        BigDecimal newUserBalance = balance.add(wonSum);
        updateUserBalance(connection, userId, newUserBalance);
        withdrawBookmakerBalance(connection, bookmakerId, wonSum);
    }

    private void withdrawLostBets(Connection connection, int raceId) throws SQLException {
        try (PreparedStatement selectLostBetsStatement = connection.prepareStatement(SELECT_LOST_BETS)) {
            selectLostBetsStatement.setInt(1, raceId);

            try (ResultSet lostBets = selectLostBetsStatement.executeQuery()) {
                while (lostBets.next()) {
                    withdrawLostBet(connection, lostBets);
                }
            }
        }
    }

    private void withdrawLostBet(Connection connection, ResultSet lostBet) throws SQLException {
        BigDecimal sum = lostBet.getBigDecimal(ColumnLabel.BET_SUM.getValue());
        int userId = lostBet.getInt(ColumnLabel.USER_ID.getValue());
        BigDecimal balance = lostBet.getBigDecimal(ColumnLabel.USER_BALANCE.getValue());
        int bookmakerId = lostBet.getInt(ColumnLabel.BOOKMAKER_ID.getValue());
        int oddsAgainst = lostBet.getInt(ColumnLabel.ODDS_AGAINST.getValue());
        BigDecimal lostSum = sum.multiply(new BigDecimal(oddsAgainst - 1));

        BigDecimal newBalance = balance.subtract(lostSum);
        updateUserBalance(connection, userId, newBalance);
        replenishBookmakerBalance(connection, bookmakerId, lostSum);
    }

    private void withdrawBookmakerBalance(Connection connection, int bookmakerId, BigDecimal withdrawalSum) throws SQLException {
        try (PreparedStatement selectBookmakerBalanceStatement = connection.prepareStatement(SELECT_BOOKMAKER_BALANCE)) {
            selectBookmakerBalanceStatement.setInt(1, bookmakerId);
            try (ResultSet bookmaker = selectBookmakerBalanceStatement.executeQuery()) {
                bookmaker.next();
                BigDecimal oldBalance = bookmaker.getBigDecimal(ColumnLabel.USER_BALANCE.getValue());
                BigDecimal newBalance = oldBalance.subtract(withdrawalSum);
                updateUserBalance(connection, bookmakerId, newBalance);
            }
        }
    }

    private void replenishBookmakerBalance(Connection connection, int bookmakerId, BigDecimal replenishmentSum) throws SQLException {
        try (PreparedStatement selectBookmakerBalanceStatement = connection.prepareStatement(SELECT_BOOKMAKER_BALANCE)) {
            selectBookmakerBalanceStatement.setInt(1, bookmakerId);
            try (ResultSet bookmaker = selectBookmakerBalanceStatement.executeQuery()) {
                bookmaker.next();
                BigDecimal oldBalance = bookmaker.getBigDecimal(ColumnLabel.USER_BALANCE.getValue());
                BigDecimal newBalance = oldBalance.add(replenishmentSum);
                updateUserBalance(connection, bookmakerId, newBalance);
            }
        }
    }

    private void updateUserBalance(Connection connection, int userId, BigDecimal newBalance) throws SQLException {
        try (PreparedStatement updateUserBalanceStatement = connection.prepareStatement(UPDATE_USER_BALANCE)) {
            updateUserBalanceStatement.setBigDecimal(1, newBalance);
            updateUserBalanceStatement.setInt(2, userId);
            updateUserBalanceStatement.executeUpdate();
        }
    }


    private void tryToRollbackTransaction(Connection connection) throws RepositoryException {
        try {
            connection.rollback();
        } catch (SQLException ex) {
            throw new RepositoryException(ex);
        }
    }

    @Override
    public List<Race> query(Specification specification) throws RepositoryException {
        if (specification == null) {
            throw new RepositoryException("Null specification");
        }
        if (!(specification instanceof SqlSpecification)) {
            throw new RepositoryException("Invalid specification type");
        }
        SqlSpecification sqlSpecification = ((SqlSpecification) specification);
        return getRacesFromDatabase(sqlSpecification);
    }

    private List<Race> getRacesFromDatabase(SqlSpecification specification) throws RepositoryException {
        try (ProxyConnection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = specification.toSqlStatement(connection);
             ResultSet resultSet = statement.executeQuery()) {

            return fillRaceListFromResultSet(resultSet);
        } catch (SQLException e) {
            throw new RepositoryException("Failed to get races", e);
        }
    }

    private List<Race> fillRaceListFromResultSet(ResultSet resultSet) throws SQLException {
        List<Race> races = new ArrayList<>();
        Race race;
        while (resultSet.next()) {
            race = buildRace(resultSet);
            races.add(race);
        }
        return races;
    }

    private Race buildRace(ResultSet resultSet) throws SQLException {
        Race race = new Race();
        race.setId(resultSet.getInt(ColumnLabel.RACE_ID.getValue()));
        race.setPrizeMoney(resultSet.getBigDecimal(ColumnLabel.RACE_PRIZE_MONEY.getValue()));
        race.setHorseWinnerId(resultSet.getInt(ColumnLabel.HORSE_WINNER_ID.getValue()));
        race.setDate(new Date(resultSet.getTimestamp(ColumnLabel.RACE_DATE.getValue()).getTime()));
        race.setDistance(resultSet.getInt(ColumnLabel.RACE_DISTANCE.getValue()));
        race.setLocation(resultSet.getString(ColumnLabel.RACE_LOCATION.getValue()));
        return race;
    }
}
