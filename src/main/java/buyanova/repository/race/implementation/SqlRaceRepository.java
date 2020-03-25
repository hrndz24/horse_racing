package buyanova.repository.race.implementation;

import buyanova.entity.Race;
import buyanova.exception.RepositoryException;
import buyanova.pool.ConnectionPool;
import buyanova.pool.ProxyConnection;
import buyanova.repository.ColumnLabel;
import buyanova.repository.race.RaceRepository;
import buyanova.specification.Specification;
import buyanova.specification.sql.SqlSpecification;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of {@code RaceRepository} interface that uses sql database as a data source.
 *
 * @author Natalie
 * @see buyanova.repository.race.RaceRepository
 */
public enum SqlRaceRepository implements RaceRepository {

    INSTANCE;

    private static final String INSERT_QUERY = "INSERT INTO races(race_prize_money," +
            "horse_winner_id, race_date, race_distance) VALUES(?,?,?,?)";

    private static final String REMOVE_QUERY = "DELETE FROM races WHERE race_id = ?";

    private static final String UPDATE_QUERY = "UPDATE races SET race_prize_money = ?," +
            "horse_winner_id = ?, race_date = ?, race_distance = ? WHERE race_id = ?";

    @Override
    public void add(Race race) throws RepositoryException {
        try (ProxyConnection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_QUERY)) {

            statement.setBigDecimal(1, race.getPrizeMoney());
            statement.setInt(2, race.getHorseWinnerId());
            statement.setDate(3, new java.sql.Date(race.getDate().getTime()));
            statement.setInt(4, race.getDistance());

            statement.executeUpdate();
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
            statement.setInt(2, race.getHorseWinnerId());
            statement.setDate(3, (java.sql.Date) race.getDate());
            statement.setInt(4, race.getDistance());
            statement.setInt(5, race.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException("Failed to update race", e);
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

        List<Race> races = new ArrayList<>();
        SqlSpecification sqlSpecification = ((SqlSpecification) specification);
        try (ProxyConnection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = sqlSpecification.toSqlStatement(connection);
             ResultSet resultSet = statement.executeQuery()) {

            Race race;
            while (resultSet.next()) {
                race = buildRace(resultSet);
                races.add(race);
            }
        } catch (SQLException e) {
            throw new RepositoryException("Failed to get races", e);
        }
        return races;
    }

    private Race buildRace(ResultSet resultSet) throws SQLException {
        Race race = new Race();
        race.setId(resultSet.getInt(ColumnLabel.RACE_ID.getValue()));
        race.setPrizeMoney(resultSet.getBigDecimal(ColumnLabel.RACE_PRIZE_MONEY.getValue()));
        race.setHorseWinnerId(resultSet.getInt(ColumnLabel.HORSE_WINNER_ID.getValue()));
        race.setDate(resultSet.getDate(ColumnLabel.RACE_DATE.getValue()));
        race.setDistance(resultSet.getInt(ColumnLabel.RACE_DISTANCE.getValue()));
        return race;
    }
}
