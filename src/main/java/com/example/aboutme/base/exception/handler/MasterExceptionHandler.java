package com.example.aboutme.base.exception.handler;

import com.example.aboutme.base.Code;
import com.example.aboutme.base.ResponseDto;
import com.example.aboutme.base.exception.GeneralException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice(annotations = {RestController.class})
@Slf4j
public class MasterExceptionHandler extends ResponseEntityExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity<Object> validation(ConstraintViolationException e, WebRequest request) {
        return handleExceptionInternal(e, Code._UNAUTHORIZED, request);
    }

    @ExceptionHandler(GeneralException.class)
    public ResponseEntity<Object> general(GeneralException e, WebRequest request) {
        log.error("에러 발생: {}", e.getMessage());
        return handleExceptionInternal(e, e.getErrorCode(), request);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity<Object> exception(Exception e, WebRequest request) {
        e.printStackTrace();
        return handleExceptionInternalFalse(e, Code._INTERNAL_SERVER_ERROR, HttpHeaders.EMPTY, Code._INTERNAL_SERVER_ERROR.getHttpStatus(),request);
    }

    // 이게 @valid 예외 처리하는건가?
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("validation 에러 발생?");
        return super.handleMethodArgumentNotValid(ex, headers, status, request);
    }

    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body,
                                                             HttpHeaders headers, HttpStatus status, WebRequest request) {

        log.info("At exception handler");
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) request;
        HttpServletRequest servletRequest = requestAttributes.getRequest();

        String contentType = request.getHeader("Content-Type");
        log.info("Content-Type : {}", contentType);
        log.error("발생한 에러의 로그 :", ex);
        return handleExceptionInternal(ex, Code.valueOf(status), headers, status, request);
    }


    private ResponseEntity<Object> handleExceptionInternal(Exception e, Code errorCode,
                                                           WebRequest request) {
        return handleExceptionInternal(e, errorCode, HttpHeaders.EMPTY, errorCode.getHttpStatus(),
                request);
    }


    private ResponseEntity<Object> handleExceptionInternal(Exception e, Code errorCode,
                                                           HttpHeaders headers, HttpStatus status, WebRequest request) {
        ResponseDto<Object> body = ResponseDto.onFailure(errorCode, null);
        return super.handleExceptionInternal(
                e,
                body,
                headers,
                status,
                request
        );
    }

    private ResponseEntity<Object> handleExceptionInternalFalse(Exception e, Code errorCode,
                                                                HttpHeaders headers, HttpStatus status, WebRequest request) {
        ResponseDto<Object> body = ResponseDto.onFailure(errorCode, null);
        return super.handleExceptionInternal(
                e,
                body,
                headers,
                status,
                request
        );
    }
}
