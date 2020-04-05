package com.buyanova.pool;

import com.buyanova.exception.ConnectionPoolException;
import com.buyanova.exception.NoJDBCPropertiesException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * A multithreading storage of project database connections
 * presented as instances of {@code ProxyConnection} class.
 * <p>
 * Any class that performs database access should get
 * {@code ProxyConnection} from here using {@code getConnection} method.
 * <p>
 * Note: method {@code init} should be called before any other method,
 * otherwise other methods would be forced to wait
 * infinitely until {@code availableConnections} are filled.
 *
 * @author Natalie
 * @see com.buyanova.pool.ProxyConnection
 */
public enum ConnectionPool {

    INSTANCE;

    private Logger logger = LogManager.getLogger(ConnectionPool.class);

    /**
     * The number of database connections stored.
     */
    private static final int POOL_SIZE = 32;

    /**
     * Storage of database connections that are not taken.
     */
    private BlockingQueue<ProxyConnection> availableConnections;

    /**
     * Storage of database connections that are currently executed.
     */
    private Queue<ProxyConnection> usedConnections;

    /**
     * The value is used to declare that the pool was created.
     */
    private AtomicBoolean isInitialized = new AtomicBoolean(false);

    /**
     * Properties file with url, driver, user and password information at least
     */
    private Properties properties;

    /**
     * Database properties filename
     */
    private static final String DATABASE_PROPERTIES_PATH = "database.properties";

    /**
     * Key for url in properties
     */
    private static final String URL_KEY = "url";

    /**
     * Key for driver name in properties
     */
    private static final String DRIVER_KEY = "driver";

    /**
     * Database url
     */
    private String url;

    /**
     * Database driver name
     */
    private String driver;


    ConnectionPool() {
        availableConnections = new LinkedBlockingQueue<>(POOL_SIZE);
        usedConnections = new ArrayDeque<>();
    }

    /**
     * Initializes pool with database connections.
     * <p>
     * Does its job only at a first call.
     *
     * @throws ConnectionPoolException If no or invalid jdbc properties are provided
     *                                 or a database access error occurs
     */
    public void init() throws ConnectionPoolException {
        if (!isInitialized.get()) {
            try {
                loadDatabaseProperties();
                Class.forName(driver);
                fillAvailableConnections();
                isInitialized.set(true);
            } catch (SQLException | NoJDBCPropertiesException | ClassNotFoundException e) {
                throw new ConnectionPoolException("Failed to initialize connection pool", e);
            }
        }
    }

    /**
     * Moves a connection from {@code availableConnections}
     * to {@code usedConnections} and then returns it.
     *
     * @return database connection
     */
    public ProxyConnection getConnection() {
        ProxyConnection connection = null;
        try {
            connection = availableConnections.take();
            usedConnections.add(connection);
        } catch (InterruptedException e) {
            logger.warn(e);
        }
        return connection;
    }

    /*
     * Package private method used in ProxyConnection so that to prevent closing
     * database connection and make it reusable.
     * */
    void releaseConnection(ProxyConnection connection) {
        usedConnections.remove(connection);
        availableConnections.add(connection);
    }

    /**
     * Clears the pool by closing all database connections stored in it and deregistering drivers.
     * <p>
     * After calling this method it's impossible to reinitialize the pool.
     *
     * @throws ConnectionPoolException If a database access error occurs
     */
    public void destroyPool() throws ConnectionPoolException {
        for (int i = 0; i < POOL_SIZE; i++) {
            try {
                availableConnections.take().closeInPool();
            } catch (SQLException | InterruptedException e) {
                throw new ConnectionPoolException("Failed to close connection pool", e);
            }
        }
        deregisterDrivers();
    }

    private void fillAvailableConnections() throws SQLException {
        ProxyConnection connection;
        for (int i = 0; i < POOL_SIZE; i++) {
            connection = new ProxyConnection(DriverManager.getConnection(url, properties));
            availableConnections.add(connection);
        }
    }

    private void loadDatabaseProperties() throws NoJDBCPropertiesException {
        properties = new Properties();
        ClassLoader classLoader = this.getClass().getClassLoader();
        try {
            properties.load(classLoader.getResourceAsStream(DATABASE_PROPERTIES_PATH));
            url = properties.getProperty(URL_KEY);
            driver = properties.getProperty(DRIVER_KEY);
        } catch (IOException e) {
            throw new NoJDBCPropertiesException("Failed to load database properties");
        }
    }

    private void deregisterDrivers() {
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            try {
                DriverManager.deregisterDriver(drivers.nextElement());
            } catch (SQLException e) {
                logger.warn(e);
            }
        }
    }
}
