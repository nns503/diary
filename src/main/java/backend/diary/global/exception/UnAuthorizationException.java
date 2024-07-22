package backend.diary.global.exception;

public class UnAuthorizationException extends DefaultException {
    public UnAuthorizationException(String message) {
        super(message);
    }

    public UnAuthorizationException(Throwable cause) {
        super(cause);
    }

    public UnAuthorizationException(String message, Throwable cause) {
        super(message, cause);
    }
}
