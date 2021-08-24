package org.sergei.metadata.selector.exception;

/**
 * @author Sergei Visotsky
 */
public class DataAccessException extends RuntimeException {

    public DataAccessException() {
        super();
    }

    public DataAccessException(String message) {
        super(message);
    }

    public DataAccessException(Throwable cause, String message, Object... params) {
        super(String.format(message.replace("{}", "%s"), params), cause);
    }

    public DataAccessException(Throwable cause) {
        super(cause);
    }
}
