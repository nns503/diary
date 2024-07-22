package backend.diary.global.auth.exception;

import backend.diary.global.exception.DuplicationException;

public class DuplicationNicknameException extends DuplicationException {

    private static final String DEFAULT_MESSAGE = "이미 존재하는 닉네임입니다.";

    public DuplicationNicknameException(){
        super(DEFAULT_MESSAGE);
    }

    public DuplicationNicknameException(String message) {
        super(message);
    }

    public DuplicationNicknameException(Throwable cause) {
        super(cause);
    }

    public DuplicationNicknameException(String message, Throwable cause) {
        super(message, cause);
    }
}
