package backend.diary.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class ExceptionAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(DefaultException.class)
    public ResponseEntity<ErrorResult> handleDefaultException(DefaultException e) {
        return new ResponseEntity<>(
                new ErrorResult("500_ INTERNAL_SERVER_ERROR", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResult> handleDefaultException(BadRequestException e) {
        return new ResponseEntity<>(
                new ErrorResult("400_BAD_REQUEST", e.getMessage()), HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResult> handleDefaultException(UnauthorizedException e) {
        return new ResponseEntity<>(
                new ErrorResult("401_UNAUTHORIZED", e.getMessage()), HttpStatus.UNAUTHORIZED
        );
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResult> handleDefaultException(NotFoundException e) {
        return new ResponseEntity<>(
                new ErrorResult("404_NOT_FOUND", e.getMessage()), HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(DuplicationException.class)
    public ResponseEntity<ErrorResult> handleDefaultException(DuplicationException e) {
        return new ResponseEntity<>(
                new ErrorResult("409_CONFLICT", e.getMessage()), HttpStatus.CONFLICT
        );
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach(error -> {
            String errorKey = (error instanceof FieldError) ? ((FieldError) error).getField() : error.getObjectName();
            errors.put(errorKey, error.getDefaultMessage());
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
