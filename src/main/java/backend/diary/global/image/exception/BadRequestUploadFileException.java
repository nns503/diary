package backend.diary.global.image.exception;

import backend.diary.global.exception.BadRequestException;

public class BadRequestUploadFileException extends BadRequestException {

    private static final String DEFAULT_MESSAGE = "파일이 존재하지 않습니다";

    public BadRequestUploadFileException() {
        super(DEFAULT_MESSAGE);
    }

    public BadRequestUploadFileException(String message) {
        super(message);
    }

    public BadRequestUploadFileException(Throwable cause) {
        super(cause);
    }

    public BadRequestUploadFileException(String message, Throwable cause) {
        super(message, cause);
    }
}
