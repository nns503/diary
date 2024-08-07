package backend.diary.domain.like.exception;

import backend.diary.global.exception.BadRequestException;

public class AlreadyLikedException extends BadRequestException {

    private static final String DEFAULT_MESSAGE = "이미 좋아요한 글입니다.";

    public AlreadyLikedException() {
        super(DEFAULT_MESSAGE);
    }

    public AlreadyLikedException(String message) {
        super(message);
    }

    public AlreadyLikedException(Throwable cause) {
        super(cause);
    }

    public AlreadyLikedException(String message, Throwable cause) {
        super(message, cause);
    }
}
