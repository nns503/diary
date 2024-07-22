package backend.diary.global.exception;

public class DuplicationException extends DefaultException{
    public DuplicationException(String message) {
        super(message);
    }

    public DuplicationException(Throwable cause) {
        super(cause);
    }

    public DuplicationException(String message, Throwable cause) {
        super(message, cause);
    }
}
