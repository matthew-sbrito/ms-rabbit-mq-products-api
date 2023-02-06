package br.com.techsoft.productapi.core.exception;

import br.com.techsoft.productapi.core.ResponseError;
import org.springframework.http.HttpStatus;

public class HttpException extends RuntimeException {
    HttpStatus httpStatus;

    public HttpException(HttpStatus httpStatus, String message) {
        super(message);

        this.httpStatus = httpStatus;
    }

    public Integer getStatusCode() {
        return httpStatus.value();
    }

    public ResponseError getError() {
        return ResponseError.builder()
                .status(httpStatus.value())
                .reason(httpStatus.getReasonPhrase())
                .message(getMessage())
                .build();
    }
}
