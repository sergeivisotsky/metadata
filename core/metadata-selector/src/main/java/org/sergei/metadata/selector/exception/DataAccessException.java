package org.sergei.metadata.selector.exception;

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
