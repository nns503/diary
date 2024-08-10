package backend.diary.domain.follow.exception;

import backend.diary.global.exception.BadRequestException;

public class AlreadyFollowException extends BadRequestException {

    private static final String DEFAULT_MESSAGE = "이미 팔로우한 대상입니다.";

    public AlreadyFollowException() {
        super(DEFAULT_MESSAGE);
    }

    public AlreadyFollowException(String message) {
        super(message);
    }

    public AlreadyFollowException(Throwable cause) {
        super(cause);
    }

    public AlreadyFollowException(String message, Throwable cause) {
        super(message, cause);
    }
}
