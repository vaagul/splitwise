package com.atlantis.splitwise.utils.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;

@ControllerAdvice
@RestController
@Slf4j
public class CustomizedExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(InvalidTransactionException.class)
    public ResponseEntity<ExceptionResponse> customException(InvalidTransactionException ex){
        ExceptionResponse response = new ExceptionResponse();
        response.setErrorCode(HttpStatus.BAD_REQUEST);
        response.setErrorMessage(ex.getMessage());
        response.setTimestamp(Instant.now());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
