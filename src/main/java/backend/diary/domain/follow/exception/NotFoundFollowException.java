package backend.diary.domain.follow.exception;

import backend.diary.global.exception.NotFoundException;

public class NotFoundFollowException extends NotFoundException {
    private static final String DEFAULT_MESSAGE = "아직 팔로우하지 않은 대상입니다.";


    public NotFoundFollowException() {
        super(DEFAULT_MESSAGE);
    }

    public NotFoundFollowException(String message) {
        super(message);
    }

    public NotFoundFollowException(Throwable cause) {
        super(cause);
    }

    public NotFoundFollowException(String message, Throwable cause) {
        super(message, cause);
    }
}
