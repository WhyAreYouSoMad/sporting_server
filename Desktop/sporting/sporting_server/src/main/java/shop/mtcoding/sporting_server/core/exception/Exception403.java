package shop.mtcoding.sporting_server.core.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import shop.mtcoding.sporting_server.core.dto.ResponseDTO;

// 권한 없음
@Getter
public class Exception403 extends RuntimeException {
    public Exception403(String message) {
        super(message);
    }

    public ResponseDTO<?> body() {
        ResponseDTO<String> responseDto = new ResponseDTO<>();
        responseDto.fail(HttpStatus.FORBIDDEN, "forbidden", getMessage());
        return responseDto;
    }

    public HttpStatus status() {
        return HttpStatus.FORBIDDEN;
    }
}