package backend.diary.global.exception;

public record ErrorResult(
        String code,
        String message
) { }
