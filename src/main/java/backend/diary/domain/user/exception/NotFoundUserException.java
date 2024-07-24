package backend.diary.domain.user.exception;

import backend.diary.global.exception.NotFoundException;

public class NotFoundUserException extends NotFoundException {

    private static final String DEFAULT_MESSAGE = "회원이 존재하지 않습니다.";

    public NotFoundUserException() {
        super(DEFAULT_MESSAGE);
    }

    public NotFoundUserException(String message) {
        super(message);
    }

    public NotFoundUserException(Throwable cause) {
        super(cause);
    }

    public NotFoundUserException(String message, Throwable cause) {
        super(message, cause);
    }
}
