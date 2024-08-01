package backend.diary.domain.Article.exception;

import backend.diary.global.exception.BadRequestException;

public class ArticleAlreadyDeletedException extends BadRequestException {

    private static final String DEFAULT_MESSAGE = "이미 삭제된 게시글입니다.";

    public ArticleAlreadyDeletedException() {
        super(DEFAULT_MESSAGE);
    }

    public ArticleAlreadyDeletedException(String message) {
        super(message);
    }

    public ArticleAlreadyDeletedException(Throwable cause) {
        super(cause);
    }

    public ArticleAlreadyDeletedException(String message, Throwable cause) {
        super(message, cause);
    }
}
