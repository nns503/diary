package backend.diary.domain.community.exception;

import backend.diary.global.exception.NotFoundException;

public class NotFoundArticleException extends NotFoundException {

    private static final String DEFAULT_MESSAGE = "존재하지 않는 게시글입니다.";

    public NotFoundArticleException() {
        super(DEFAULT_MESSAGE);
    }

    public NotFoundArticleException(String message) {
        super(message);
    }

    public NotFoundArticleException(Throwable cause) {
        super(cause);
    }

    public NotFoundArticleException(String message, Throwable cause) {
        super(message, cause);
    }
}
