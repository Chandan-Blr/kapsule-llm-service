package com.kapsulesolution.llm.exception;

import org.springframework.http.HttpStatus;

public class LLMException extends BaseException {

    public LLMException(String message, HttpStatus status) {
        super(message, status);
    }
}