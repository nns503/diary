package backend.diary.domain.like.exception;

import backend.diary.global.exception.NotFoundException;

public class NotFoundLikedException extends NotFoundException {

    private static final String DEFAULT_MESSAGE = "이미 좋아요를 하지 않았습니다.";

    public NotFoundLikedException() {
        super(DEFAULT_MESSAGE);
    }

    public NotFoundLikedException(String message) {
        super(message);
    }

    public NotFoundLikedException(Throwable cause) {
        super(cause);
    }

    public NotFoundLikedException(String message, Throwable cause) {
        super(message, cause);
    }
}
