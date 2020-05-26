package com.buyanova.repository.horse.impl;

import com.buyanova.entity.Horse;
import com.buyanova.exception.RepositoryException;
import com.buyanova.pool.ConnectionPool;
import com.buyanova.repository.ColumnLabel;
import com.buyanova.repository.horse.HorseRepository;
import com.buyanova.specification.Specification;
import com.buyanova.specification.SqlSpecification;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of {@code HorseRepository} interface
 * that uses sql database as a data source.
 *
 * @author Natalie
 * @see com.buyanova.repository.horse.HorseRepository
 */
public enum SqlHorseRepository implements HorseRepository {

    INSTANCE;

    private static final String INSERT_QUERY = "INSERT INTO horses(horse_name, horse_breed," +
            "horse_age, is_performing, races_won_number, races_lost_number) VALUES(?, ?, ?, ?, ?, ?)";

    private static final String UPDATE_QUERY = "UPDATE horses SET horse_name = ?," +
            "horse_breed = ?, horse_age = ?, races_won_number = ?, races_lost_number = ?, is_performing = ? WHERE horse_id = ?";

    private static final String REMOVE_QUERY = "UPDATE horses SET is_performing = false WHERE horse_id = ?";

    @Override
    public void add(Horse horse) throws RepositoryException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, horse.getName());
            statement.setString(2, horse.getBreed());
            statement.setInt(3, horse.getAge());
            statement.setBoolean(4, horse.isPerforming());
            statement.setInt(5, horse.getRacesWonNumber());
            statement.setInt(6, horse.getRacesLostNumber());
            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    horse.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Failed to add horse", e);
        }
    }

    @Override
    public void remove(Horse horse) throws RepositoryException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(REMOVE_QUERY)) {

            statement.setInt(1, horse.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException("Failed to remove horse", e);
        }
    }

    @Override
    public void update(Horse horse) throws RepositoryException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {

            statement.setString(1, horse.getName());
            statement.setString(2, horse.getBreed());
            statement.setInt(3, horse.getAge());
            statement.setInt(4, horse.getRacesWonNumber());
            statement.setInt(5, horse.getRacesLostNumber());
            statement.setBoolean(6, horse.isPerforming());
            statement.setInt(7, horse.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException("Failed to update horse", e);
        }
    }

    @Override
    public List<Horse> query(Specification specification) throws RepositoryException {
        if (specification == null) {
            throw new RepositoryException("Null specification");
        }
        if (!(specification instanceof SqlSpecification)) {
            throw new RepositoryException("Invalid specification type");
        }
        SqlSpecification sqlSpecification = (SqlSpecification) specification;
        return getHorsesFromDatabase(sqlSpecification);
    }

    private List<Horse> getHorsesFromDatabase(SqlSpecification specification) throws RepositoryException {
        List<Horse> horses = new ArrayList<>();
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = specification.toSqlStatement(connection);
             ResultSet resultSet = statement.executeQuery()) {

            Horse horse;
            while (resultSet.next()) {
                horse = buildHorse(resultSet);
                horses.add(horse);
            }
        } catch (SQLException e) {
            throw new RepositoryException("Failed to get horses", e);
        }
        return horses;
    }

    private Horse buildHorse(ResultSet resultSet) throws SQLException {
        Horse horse = new Horse();
        horse.setId(resultSet.getInt(ColumnLabel.HORSE_ID.getValue()));
        horse.setName(resultSet.getString(ColumnLabel.HORSE_NAME.getValue()));
        horse.setBreed(resultSet.getString(ColumnLabel.HORSE_BREED.getValue()));
        horse.setAge(resultSet.getInt(ColumnLabel.HORSE_AGE.getValue()));
        horse.setPerforming(resultSet.getBoolean(ColumnLabel.IS_PERFORMING.getValue()));
        horse.setRacesWonNumber(resultSet.getInt(ColumnLabel.RACES_WON_NUMBER.getValue()));
        horse.setRacesLostNumber(resultSet.getInt(ColumnLabel.RACES_LOST_NUMBER.getValue()));
        return horse;
    }
}
