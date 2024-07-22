package backend.diary.global.auth.exception;

import backend.diary.global.exception.BadRequestException;

public class RefreshTokenNullException extends BadRequestException {

    private static final String DEFAULT_MESSAGE = "리프레쉬 토큰이 존재하지 않습니다.";

    public RefreshTokenNullException() {
        super(DEFAULT_MESSAGE);
    }

    public RefreshTokenNullException(String message) {
        super(message);
    }

    public RefreshTokenNullException(Throwable cause) {
        super(cause);
    }

    public RefreshTokenNullException(String message, Throwable cause) {
        super(message, cause);
    }
}
