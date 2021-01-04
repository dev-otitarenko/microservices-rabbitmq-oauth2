package com.maestro.app.utils.exceptions;

public class EntityRecordNotFound extends Exception {
    private static final long serialVersionUID = 1L;
    private String errorMessage;

    public EntityRecordNotFound(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }

    public EntityRecordNotFound() {
        super();
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
