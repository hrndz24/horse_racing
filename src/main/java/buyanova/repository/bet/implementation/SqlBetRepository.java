package buyanova.repository.bet.implementation;

import buyanova.entity.Bet;
import buyanova.exception.RepositoryException;
import buyanova.pool.ConnectionPool;
import buyanova.pool.ProxyConnection;
import buyanova.repository.ColumnLabel;
import buyanova.repository.bet.BetRepository;
import buyanova.specification.Specification;
import buyanova.specification.sql.SqlSpecification;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of {@code BetRepository} interface
 * that uses sql database as a data source.
 *
 * @author Natalie
 * @see buyanova.repository.bet.BetRepository
 */
public enum SqlBetRepository implements BetRepository {

    INSTANCE;

    private static final String INSERT_QUERY = "INSERT INTO bets(user_id, bet_sum, odds_id) VALUES(?,?,?)";

    private static final String REMOVE_QUERY = "DELETE FROM bets WHERE bet_id = ?";

    private static final String UPDATE_QUERY = "UPDATE bets SET bet_sum = ?, odds_id = ? WHERE bet_id = ?";

    @Override
    public void add(Bet bet) throws RepositoryException {
        try (ProxyConnection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS);
             ResultSet generatedKeys = statement.getGeneratedKeys()) {

            statement.setInt(1, bet.getUserId());
            statement.setBigDecimal(2, bet.getSum());
            statement.setInt(3, bet.getOddsId());
            statement.executeUpdate();

            if (generatedKeys.next()) {
                bet.setId(generatedKeys.getInt(ColumnLabel.BET_ID.getValue()));
            }
        } catch (SQLException e) {
            throw new RepositoryException("Failed to add bet", e);
        }
    }

    @Override
    public void remove(Bet bet) throws RepositoryException {
        try (ProxyConnection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(REMOVE_QUERY)) {

            statement.setInt(1, bet.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException("Failed to remove bet", e);
        }
    }

    @Override
    public void update(Bet bet) throws RepositoryException {
        try (ProxyConnection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {

            statement.setBigDecimal(1, bet.getSum());
            statement.setInt(2, bet.getOddsId());
            statement.setInt(3, bet.getId());

            statement.executeUpdate();
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
        try (ProxyConnection connection = ConnectionPool.INSTANCE.getConnection();
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
