package com.kapsulesolution.llm.dto;

import java.time.LocalDateTime;

public class ApiError {

    private boolean success = false;
    private String message;
    private Object details;
    private int status;
    private LocalDateTime timestamp;
    private String path;

    public ApiError() {
        this.timestamp = LocalDateTime.now();
    }

    public ApiError(String message, Object details, int status, String path) {
        this.success = false;
        this.message = message;
        this.details = details;
        this.status = status;
        this.timestamp = LocalDateTime.now();
        this.path = path;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getDetails() {
        return details;
    }

    public void setDetails(Object details) {
        this.details = details;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}