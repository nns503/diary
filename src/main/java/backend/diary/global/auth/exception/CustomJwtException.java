package backend.diary.global.auth.exception;

import backend.diary.global.exception.UnAuthorizationException;

public class CustomJwtException extends UnAuthorizationException {

    private static final String DEFAULT_MESSAGE = "JWT 토큰 인증이 실패하였습니다.";

    public CustomJwtException(){
        super(DEFAULT_MESSAGE);
    }
    public CustomJwtException(String message) {
        super(message);
    }

    public CustomJwtException(Throwable cause) {
        super(cause);
    }

    public CustomJwtException(String message, Throwable cause) {
        super(message, cause);
    }
}
