package buyanova.repository.horse.implementation;

import buyanova.entity.Horse;
import buyanova.exception.RepositoryException;
import buyanova.pool.ConnectionPool;
import buyanova.pool.ProxyConnection;
import buyanova.repository.ColumnLabel;
import buyanova.repository.horse.HorseRepository;
import buyanova.specification.Specification;
import buyanova.specification.sql.SqlSpecification;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of {@code HorseRepository} interface
 * that uses sql database as a data source.
 *
 * @author Natalie
 * @see buyanova.repository.horse.HorseRepository
 */
public enum SqlHorseRepository implements HorseRepository {

    INSTANCE;

    private static final String INSERT_QUERY = "INSERT INTO horses(jockey_id, horse_name, horse_breed," +
            "horse_age, is_performing, races_won_number, races_lost_number) VALUES(?, ?, ?, ?, ?, ?, ?)";

    private static final String UPDATE_QUERY = "UPDATE horses SET jockey_id = ?, horse_name = ?," +
            "horse_breed = ?, horse_age = ?, races_won_number = ?, races_lost_number = ? WHERE horse_id = ?";

    private static final String REMOVE_QUERY = "UPDATE horses SET is_performing = false WHERE horse_id = ?";

    @Override
    public void add(Horse horse) throws RepositoryException {
        try (ProxyConnection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS)) {

            statement.setInt(1, horse.getJockeyId());
            statement.setString(2, horse.getName());
            statement.setString(3, horse.getBreed());
            statement.setInt(4, horse.getAge());
            statement.setBoolean(5, horse.isPerforming());
            statement.setInt(6, horse.getRacesWonNumber());
            statement.setInt(7, horse.getRacesLostNumber());
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
        try (ProxyConnection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(REMOVE_QUERY)) {

            statement.setInt(1, horse.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException("Failed to remove horse", e);
        }
    }

    @Override
    public void update(Horse horse) throws RepositoryException {
        try (ProxyConnection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {

            statement.setInt(1, horse.getJockeyId());
            statement.setString(2, horse.getName());
            statement.setString(3, horse.getBreed());
            statement.setInt(4, horse.getAge());
            statement.setInt(5, horse.getRacesWonNumber());
            statement.setInt(6, horse.getRacesLostNumber());
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
        SqlSpecification sqlSpecification = ((SqlSpecification) specification);
        return getHorsesFromDatabase(sqlSpecification);
    }

    private List<Horse> getHorsesFromDatabase(SqlSpecification specification) throws RepositoryException {
        List<Horse> horses = new ArrayList<>();
        try (ProxyConnection connection = ConnectionPool.INSTANCE.getConnection();
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
        horse.setJockeyId(resultSet.getInt(ColumnLabel.JOCKEY_ID.getValue()));
        horse.setName(resultSet.getString(ColumnLabel.HORSE_NAME.getValue()));
        horse.setBreed(resultSet.getString(ColumnLabel.HORSE_BREED.getValue()));
        horse.setAge(resultSet.getInt(ColumnLabel.HORSE_AGE.getValue()));
        horse.setPerforming(resultSet.getBoolean(ColumnLabel.IS_PERFORMING.getValue()));
        horse.setRacesWonNumber(resultSet.getInt(ColumnLabel.RACES_WON_NUMBER.getValue()));
        horse.setRacesLostNumber(resultSet.getInt(ColumnLabel.RACES_LOST_NUMBER.getValue()));
        return horse;
    }
}
