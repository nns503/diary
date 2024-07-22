package backend.diary.global.exception;

public class DefaultException extends RuntimeException {
    public DefaultException(String message) {
        super(message);
    }

    public DefaultException(Throwable cause) {
        super(cause);
    }

    public DefaultException(String message, Throwable cause) {
        super(message, cause);
    }
}
