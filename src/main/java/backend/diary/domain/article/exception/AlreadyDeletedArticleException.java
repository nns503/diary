package backend.diary.domain.article.exception;

import backend.diary.global.exception.BadRequestException;

public class AlreadyDeletedArticleException extends BadRequestException {

    private static final String DEFAULT_MESSAGE = "이미 삭제된 게시글입니다.";

    public AlreadyDeletedArticleException() {
        super(DEFAULT_MESSAGE);
    }

    public AlreadyDeletedArticleException(String message) {
        super(message);
    }

    public AlreadyDeletedArticleException(Throwable cause) {
        super(cause);
    }

    public AlreadyDeletedArticleException(String message, Throwable cause) {
        super(message, cause);
    }
}
