package shop.mtcoding.sporting_server.core.advice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.sentry.Sentry;
import lombok.RequiredArgsConstructor;
import shop.mtcoding.sporting_server.core.exception.CustomValidationException;
import shop.mtcoding.sporting_server.core.exception.Exception400;
import shop.mtcoding.sporting_server.core.exception.Exception401;
import shop.mtcoding.sporting_server.core.exception.Exception403;
import shop.mtcoding.sporting_server.core.exception.Exception404;
import shop.mtcoding.sporting_server.core.exception.Exception500;
import shop.mtcoding.sporting_server.core.handler.ex.CustomException;
import shop.mtcoding.sporting_server.core.util.Script;

@RequiredArgsConstructor
@RestControllerAdvice
public class MyExceptionAdvice {

    @ExceptionHandler(Exception400.class)
    public ResponseEntity<?> badRequest(Exception400 e) {
        Sentry.captureException(e);
        return new ResponseEntity<>(e.body(), e.status());
    }

    @ExceptionHandler(Exception401.class)
    public ResponseEntity<?> unAuthorized(Exception401 e) {
        Sentry.captureException(e);
        return new ResponseEntity<>(e.body(), e.status());
    }

    @ExceptionHandler(Exception403.class)
    public ResponseEntity<?> forbidden(Exception403 e) {
        Sentry.captureException(e);
        return new ResponseEntity<>(e.body(), e.status());
    }

    @ExceptionHandler(Exception404.class)
    public ResponseEntity<?> notFound(Exception404 e) {
        Sentry.captureException(e);
        return new ResponseEntity<>(e.body(), e.status());
    }

    @ExceptionHandler(Exception500.class)
    public ResponseEntity<?> serverError(Exception500 e) {
        Sentry.captureException(e);
        return new ResponseEntity<>(e.body(), e.status());
    }

    @ExceptionHandler(CustomValidationException.class)
    public ResponseEntity<?> serverError(CustomValidationException e) {
        Sentry.captureException(e);
        return new ResponseEntity<>(e.body(), e.status());
    }

    @ExceptionHandler(CustomException.class) // 이러면 CustomException 터지면 아래 메서드가 다 낚아챔
    public ResponseEntity<?> customException(CustomException e) {
        Sentry.captureException(e);
        return new ResponseEntity<>(Script.back(e.getMessage()), e.getStatus());
        // return Script.back(e.getMessage()); // CustomException이 터지면 이 messsage 응답할 거임
    }
}
