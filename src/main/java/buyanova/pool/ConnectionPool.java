package buyanova.pool;

import buyanova.exception.NoJDBCPropertiesException;
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

public enum ConnectionPool {

    INSTANCE;

    private Logger logger = LogManager.getLogger(ConnectionPool.class);

    private static final int POOL_SIZE = 32;
    private BlockingQueue<ProxyConnection> availableConnections;
    private Queue<ProxyConnection> usedConnections;

    private Properties properties;
    private static final String URL_KEY = "url";
    private static final String DATABASE_PROPERTIES_PATH = "database.properties";
    private String url;

    public void init(){
        availableConnections = new LinkedBlockingQueue<>(POOL_SIZE);
        usedConnections = new ArrayDeque<>();
        try {
            loadDatabaseProperties();
            DriverManager.registerDriver(DriverManager.getDriver(url));
            ProxyConnection connection;
            for (int i = 0; i < POOL_SIZE; i++) {
                connection = new ProxyConnection(DriverManager.getConnection(url, properties));
                availableConnections.add(connection);
            }
        } catch (SQLException | NoJDBCPropertiesException e) {
            logger.fatal(e);
        }
    }

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

    public void releaseConnection(ProxyConnection connection) {
        usedConnections.remove(connection);
        availableConnections.add(connection);
    }

    public void destroyPool() {
        for (int i = 0; i < POOL_SIZE; i++) {
            try {
                availableConnections.take().closeInPool();
            } catch (SQLException | InterruptedException e) {
                logger.warn(e);
            }
        }
        deregisterDrivers();
    }

    private void loadDatabaseProperties() throws NoJDBCPropertiesException {
        properties = new Properties();
        ClassLoader classLoader = this.getClass().getClassLoader();
        try {
            properties.load(classLoader.getResourceAsStream(DATABASE_PROPERTIES_PATH));
            url = properties.getProperty(URL_KEY);
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
