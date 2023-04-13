package shop.mtcoding.sporting_server.core.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import shop.mtcoding.sporting_server.core.dto.ResponseDto;

// 권한 없음
@Getter
public class Exception403 extends RuntimeException {
    public Exception403(String message) {
        super(message);
    }

    public ResponseDto<?> body() {
        ResponseDto<String> responseDto = new ResponseDto<>();
        responseDto.fail(HttpStatus.FORBIDDEN, "forbidden", getMessage());
        return responseDto;
    }

    public HttpStatus status() {
        return HttpStatus.FORBIDDEN;
    }
}