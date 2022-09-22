package com.tinvio.accounting.model.exception;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
public class ValidationErrorException extends RuntimeException {
    private HttpStatus httpStatus;
    private String status;
    private String errorCode;
    private String message;

    public ValidationErrorException(HttpStatus httpStatus,
                                    String status,
                                    String errorCode,
                                    String message) {
        super(message);
        this.httpStatus = httpStatus;
        this.status = status;
        this.errorCode = errorCode;
        this.message = message;
    }
}
