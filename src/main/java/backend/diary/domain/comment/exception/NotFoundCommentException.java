package backend.diary.domain.comment.exception;

import backend.diary.global.exception.NotFoundException;

public class NotFoundCommentException extends NotFoundException {

    private static final String DEFAULT_MESSAGE = "댓글이 존재하지 않습니다.";

    public NotFoundCommentException() {
        super(DEFAULT_MESSAGE);
    }

    public NotFoundCommentException(String message) {
        super(message);
    }

    public NotFoundCommentException(Throwable cause) {
        super(cause);
    }

    public NotFoundCommentException(String message, Throwable cause) {
        super(message, cause);
    }
}
