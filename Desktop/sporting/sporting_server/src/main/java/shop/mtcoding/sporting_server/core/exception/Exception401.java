package shop.mtcoding.sporting_server.core.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import shop.mtcoding.sporting_server.core.dto.ResponseDTO;

// 인증 안됨
@Getter
public class Exception401 extends RuntimeException {
    public Exception401(String message) {
        super(message);
    }

    public ResponseDTO<?> body() {
        ResponseDTO<String> responseDto = new ResponseDTO<>();
        responseDto.fail(HttpStatus.UNAUTHORIZED, "unAuthorized", getMessage());
        return responseDto;
    }

    public HttpStatus status() {
        return HttpStatus.UNAUTHORIZED;
    }
}