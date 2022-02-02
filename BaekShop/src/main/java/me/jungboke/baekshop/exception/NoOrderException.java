package me.jungboke.baekshop.exception;

public class NoOrderException extends RuntimeException {

    public NoOrderException() {
        super();
    }

    public NoOrderException(String message) {
        super(message);
    }

    public NoOrderException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoOrderException(Throwable cause) {
        super(cause);
    }

    protected NoOrderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
