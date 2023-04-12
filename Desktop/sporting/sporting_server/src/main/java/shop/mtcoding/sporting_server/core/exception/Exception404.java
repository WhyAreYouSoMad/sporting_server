package shop.mtcoding.sporting_server.core.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import shop.mtcoding.sporting_server.core.dto.ResponseDTO;

// 리소스 없음
@Getter
public class Exception404 extends RuntimeException {
    public Exception404(String message) {
        super(message);
    }

    public ResponseDTO<?> body() {
        ResponseDTO<String> responseDto = new ResponseDTO<>();
        responseDto.fail(HttpStatus.NOT_FOUND, "notFound", getMessage());
        return responseDto;
    }

    public HttpStatus status() {
        return HttpStatus.NOT_FOUND;
    }
}