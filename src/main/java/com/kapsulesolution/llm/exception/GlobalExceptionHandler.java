package com.kapsulesolution.llm.exception;

import com.kapsulesolution.llm.dto.ApiError;

import jakarta.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(LLMException.class)
    public ResponseEntity<ApiError> handleLLMException(LLMException ex, HttpServletRequest request) {

        log.warn("LLMException: {}", ex.getMessage());

        ApiError error = new ApiError(
                ex.getMessage(),
                null,
                ex.getStatus().value(),
                request.getRequestURI()
        );

        return new ResponseEntity<>(error, ex.getStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGenericException(Exception ex, HttpServletRequest request) {

        log.error("Unhandled Exception", ex);

        ApiError error = new ApiError(
                "Internal Server Error",
                ex.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                request.getRequestURI()
        );

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}