package com.maestro.app.utils.exceptions;

public class BadRequestException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private String errorMessage;

    public BadRequestException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }

    public BadRequestException() {
        super();
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
