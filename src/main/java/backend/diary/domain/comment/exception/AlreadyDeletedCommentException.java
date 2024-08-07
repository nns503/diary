package backend.diary.domain.comment.exception;

import backend.diary.global.exception.BadRequestException;

public class AlreadyDeletedCommentException extends BadRequestException {

    private static final String DEFAULT_MESSAGE = "이미 삭제된 댓글입니다.";

    public AlreadyDeletedCommentException() {
        super(DEFAULT_MESSAGE);
    }

    public AlreadyDeletedCommentException(String message) {
        super(message);
    }

    public AlreadyDeletedCommentException(Throwable cause) {
        super(cause);
    }

    public AlreadyDeletedCommentException(String message, Throwable cause) {
        super(message, cause);
    }
}
