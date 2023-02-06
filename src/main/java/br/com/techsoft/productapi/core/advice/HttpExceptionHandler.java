package br.com.techsoft.productapi.core.advice;

import br.com.techsoft.productapi.core.ResponseError;
import br.com.techsoft.productapi.core.exception.HttpException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class HttpExceptionHandler {

    @ExceptionHandler(HttpException.class)
    public ResponseEntity<ResponseError> handle(HttpException exception) {
        log.warn("Request failed, status code: [{}], message [{}].", exception.getStatusCode(), exception.getMessage());

        return ResponseEntity
                .status(exception.getStatusCode())
                .body(exception.getError());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseError> handleDefault(Exception exception) {
        log.warn("Request failed, message [{}].", exception.getMessage());

        HttpStatus badRequest = HttpStatus.BAD_REQUEST;

        ResponseError responseError = ResponseError
                .builder()
                .reason(badRequest.getReasonPhrase())
                .message(exception.getMessage())
                .status(badRequest.value())
                .build();

        return ResponseEntity
                .status(badRequest.value())
                .body(responseError);
    }
}
