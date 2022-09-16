package com.atlantis.splitwise.utils.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.Instant;

@Data
public class ExceptionResponse {
    private String errorMessage;
    private HttpStatus errorCode;
    private Instant timestamp;
}
