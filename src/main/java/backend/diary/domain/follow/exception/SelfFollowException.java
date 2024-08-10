package backend.diary.domain.follow.exception;

import backend.diary.global.exception.BadRequestException;

public class SelfFollowException extends BadRequestException {

    private static final String DEFAULT_MESSAGE = "자신의 계정에는 팔로우 할 수 없습니다";

    public SelfFollowException() {
        super(DEFAULT_MESSAGE);
    }

    public SelfFollowException(String message) {
        super(message);
    }

    public SelfFollowException(Throwable cause) {
        super(cause);
    }

    public SelfFollowException(String message, Throwable cause) {
        super(message, cause);
    }
}
