package com.tinvio.accounting.exception;

import com.tinvio.accounting.model.enums.ResultCode;
import com.tinvio.accounting.model.exception.ValidationErrorException;
import com.tinvio.accounting.model.response.BaseResponse;
import com.tinvio.accounting.model.response.ErrorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
@RestController
@Slf4j
@RequiredArgsConstructor
public class GlobalRestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
                                                               HttpStatus status, WebRequest request) {
        var httpStatus = HttpStatus.BAD_REQUEST;
        log.error("inside handleMethodArgumentNotValid: error {} {}", ex.getLocalizedMessage(), ExceptionUtils.getRootCauseMessage(ex.getCause()));
        var response = BaseResponse.builder()
                .status(ResultCode.PE_C_ERROR_001.getStatus())
                .code(ResultCode.PE_C_ERROR_001.name())
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(response, httpStatus);
    }

    @Override
    public ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
                                                                HttpStatus status, WebRequest request) {
        var httpStatus = HttpStatus.NOT_FOUND;
        log.error("inside handleNoHandlerFoundException: error {} {}", ex.getLocalizedMessage(), ExceptionUtils.getRootCauseMessage(ex.getCause()));
        var response = BaseResponse.builder()
                .status(ResultCode.PE_S_ERROR_007.getStatus())
                .code(ResultCode.PE_S_ERROR_007.name())
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(response, httpStatus);
    }

    @Override
    public ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
                                                                       HttpHeaders headers, HttpStatus status, WebRequest request) {
        var httpStatus = HttpStatus.BAD_REQUEST;
        log.error("inside handleMissingServletRequestParameter: error {} {}", ex.getLocalizedMessage(), ExceptionUtils.getRootCauseMessage(ex.getCause()));
        var response = BaseResponse.builder()
                .status(ResultCode.PE_C_ERROR_001.getStatus())
                .code(ResultCode.PE_C_ERROR_001.name())
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(response, httpStatus);
    }

    @Override
    public ResponseEntity<Object> handleMissingServletRequestPart(MissingServletRequestPartException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        var httpStatus = HttpStatus.BAD_REQUEST;
        log.error("inside handleMissingServletRequestPart: error {} {}", ex.getLocalizedMessage(), ExceptionUtils.getRootCauseMessage(ex.getCause()));
        var response = BaseResponse.builder()
                .status(ResultCode.PE_C_ERROR_001.getStatus())
                .code(ResultCode.PE_C_ERROR_001.name())
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(response, httpStatus);
    }

    @Override
    public ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers,
                                                               HttpStatus status, WebRequest request) {
        var httpStatus = HttpStatus.BAD_REQUEST;
        log.error("inside handleHttpMessageNotReadable: error {} {}", ex.getLocalizedMessage(), ExceptionUtils.getRootCauseMessage(ex.getCause()));
        var response = BaseResponse.builder()
                .status(ResultCode.PE_C_ERROR_001.getStatus())
                .code(ResultCode.PE_C_ERROR_001.name())
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(response, httpStatus);
    }

    @Override
    protected ResponseEntity<Object> handleServletRequestBindingException(ServletRequestBindingException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        var httpStatus = HttpStatus.BAD_REQUEST;
        log.error("inside handleServletRequestBindingException: error {} {}", ex.getLocalizedMessage(), ExceptionUtils.getRootCauseMessage(ex.getCause()));
        var response = BaseResponse.builder()
                .status(ResultCode.PE_C_ERROR_001.getStatus())
                .code(ResultCode.PE_C_ERROR_001.name())
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(response, httpStatus);
    }

    @ExceptionHandler(value = {Exception.class})
    protected ResponseEntity<Object> handleGenericException(Exception ex) {
        var httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        ex.printStackTrace();
        log.error("inside handleGenericException: error {} {}", ex.getLocalizedMessage(), ExceptionUtils.getRootCauseMessage(ex.getCause()));
        var response = BaseResponse.builder()
                .message(ex.getMessage())
                .status(ResultCode.PE_S_ERROR_001.getStatus())
                .code(ResultCode.PE_S_ERROR_001.name())
                .build();
        return new ResponseEntity<>(response, httpStatus);
    }

    @ExceptionHandler({MissingRequestHeaderException.class, MethodArgumentTypeMismatchException.class})
    public ResponseEntity<Object> handleInvalidRequest(Exception ex) {
        var httpStatus = HttpStatus.BAD_REQUEST;
        log.error("handleInvalidRequest: error {} ", ex.getLocalizedMessage());
        var response = BaseResponse.builder()
                .status(ResultCode.PE_C_ERROR_001.getStatus())
                .code(ResultCode.PE_C_ERROR_001.name())
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(response, httpStatus);
    }

    @ExceptionHandler({ValidationErrorException.class})
    protected ResponseEntity<?> handleValidationException(ValidationErrorException ex) {
        log.error("handleValidationException:", ex.getMessage());
        var response = BaseResponse.builder()
                .status(ex.getStatus())
                .code(ex.getErrorCode())
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(response, ex.getHttpStatus());
    }
}
