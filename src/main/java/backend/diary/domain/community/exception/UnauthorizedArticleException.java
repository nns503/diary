package backend.diary.domain.community.exception;

import backend.diary.global.exception.UnauthorizedException;

public class UnauthorizedArticleException extends UnauthorizedException {

    private static final String DEFAULT_MESSAGE = "해당 게시글에 대한 권한이 없습니다.";

    public UnauthorizedArticleException(){
        super(DEFAULT_MESSAGE);
    }

    public UnauthorizedArticleException(String message) {
        super(message);
    }

    public UnauthorizedArticleException(Throwable cause) {
        super(cause);
    }

    public UnauthorizedArticleException(String message, Throwable cause) {
        super(message, cause);
    }
}
