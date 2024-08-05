package backend.diary.domain.comment.exception;

import backend.diary.global.exception.UnauthorizedException;

public class UnauthorizedCommentException extends UnauthorizedException {

    private static final String DEFAULT_MESSAGE = "해당 댓글에 대한 권한이 없습니다.";

    public UnauthorizedCommentException() {
        super(DEFAULT_MESSAGE);
    }

    public UnauthorizedCommentException(String message) {
        super(message);
    }

    public UnauthorizedCommentException(Throwable cause) {
        super(cause);
    }

    public UnauthorizedCommentException(String message, Throwable cause) {
        super(message, cause);
    }
}
