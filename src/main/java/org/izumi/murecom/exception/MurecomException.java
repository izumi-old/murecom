package org.izumi.murecom.exception;

public class MurecomException extends RuntimeException {
    public MurecomException() {
        super();
    }

    public MurecomException(String message) {
        super(message);
    }

    public MurecomException(String message, Throwable cause) {
        super(message, cause);
    }

    public MurecomException(Throwable cause) {
        super(cause);
    }
}
