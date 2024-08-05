package backend.diary.domain.comment.exception;

import backend.diary.global.exception.BadRequestException;

public class CommentAlreadyDeletedException extends BadRequestException {

    private static final String DEFAULT_MESSAGE = "이미 삭제된 댓글입니다.";

    public CommentAlreadyDeletedException() {
        super(DEFAULT_MESSAGE);
    }

    public CommentAlreadyDeletedException(String message) {
        super(message);
    }

    public CommentAlreadyDeletedException(Throwable cause) {
        super(cause);
    }

    public CommentAlreadyDeletedException(String message, Throwable cause) {
        super(message, cause);
    }
}
