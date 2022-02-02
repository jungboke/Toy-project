package me.jungboke.baekshop.exception;

public class CompDeliveryException extends RuntimeException {

    public CompDeliveryException() {
        super();
    }

    public CompDeliveryException(String message) {
        super(message);
    }

    public CompDeliveryException(String message, Throwable cause) {
        super(message, cause);
    }

    public CompDeliveryException(Throwable cause) {
        super(cause);
    }

    protected CompDeliveryException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
