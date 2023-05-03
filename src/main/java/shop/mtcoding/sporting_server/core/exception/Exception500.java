package shop.mtcoding.sporting_server.core.exception;

import org.springframework.http.HttpStatus;

import io.sentry.Sentry;
import lombok.Getter;
import shop.mtcoding.sporting_server.core.dto.ResponseDto;

// 서버 에러
@Getter
public class Exception500 extends RuntimeException {
    public Exception500(String message) {
        super(message);
    }

    public ResponseDto<?> body() {
        ResponseDto<String> responseDto = new ResponseDto<>();
        responseDto.fail(HttpStatus.INTERNAL_SERVER_ERROR, "serverError", getMessage());
        return responseDto;
    }

    public HttpStatus status() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}