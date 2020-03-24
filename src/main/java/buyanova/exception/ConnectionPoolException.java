package buyanova.exception;

/**
 * Exception associated with the pool of project database connections.
 * <p>
 * Is thrown in the following cases:
 * <ul>
 *     <li>The {@code init} method in {@code ConnectionPool} class.
 *     <li>The {@code destroy} method in {@code ConnectionPool} class.
 * </ul>
 *
 * @see buyanova.pool.ConnectionPool
 * @author Natalie
 * */
public class ConnectionPoolException extends Exception {
    public ConnectionPoolException() {
    }

    public ConnectionPoolException(String message) {
        super(message);
    }

    public ConnectionPoolException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConnectionPoolException(Throwable cause) {
        super(cause);
    }

    public ConnectionPoolException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
