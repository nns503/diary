package backend.diary.global.auth.exception;

import backend.diary.global.exception.DuplicationException;

public class DuplicationUsernameException extends DuplicationException {

    private static final String DEFAULT_MESSAGE = "이미 존재하는 아이디입니다.";

    public DuplicationUsernameException(){
        super(DEFAULT_MESSAGE);
    }

    public DuplicationUsernameException(String message) {
        super(message);
    }

    public DuplicationUsernameException(Throwable cause) {
        super(cause);
    }

    public DuplicationUsernameException(String message, Throwable cause) {
        super(message, cause);
    }
}
