package com.buyanova.repository.race.impl;

import com.buyanova.entity.Race;
import com.buyanova.exception.RepositoryException;
import com.buyanova.pool.ConnectionPool;
import com.buyanova.repository.ColumnLabel;
import com.buyanova.repository.race.RaceRepository;
import com.buyanova.specification.Specification;
import com.buyanova.specification.SqlSpecification;

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

    private static final String SET_RACE_RESULTS = "{CALL usp_SetRaceResults(?, ?)}";

    private static final String GET_PAST_RACES_COUNT = "SELECT COUNT(*) FROM races WHERE race_date < now()";


    @Override
    public void addHorseToRace(int horseId, int raceId) throws RepositoryException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
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
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
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
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
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
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(REMOVE_QUERY)) {

            statement.setInt(1, race.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException("Failed to remove race", e);
        }
    }

    @Override
    public void update(Race race) throws RepositoryException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
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
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             CallableStatement statement = connection.prepareCall(SET_RACE_RESULTS)) {
            statement.setInt(1, race.getId());
            statement.setInt(2, race.getHorseWinnerId());
            statement.execute();
        } catch (SQLException e) {
            throw new RepositoryException("Failed to set race results in database", e);
        }
    }

    @Override
    public int getNumberOfPastRacesRecords() throws RepositoryException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_PAST_RACES_COUNT);
             ResultSet resultSet = statement.executeQuery()) {

            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            throw new RepositoryException("Failed to get number of records", e);
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
        SqlSpecification sqlSpecification = (SqlSpecification) specification;
        return getRacesFromDatabase(sqlSpecification);
    }

    private List<Race> getRacesFromDatabase(SqlSpecification specification) throws RepositoryException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
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
