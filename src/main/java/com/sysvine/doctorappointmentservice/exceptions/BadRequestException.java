package com.sysvine.doctorappointmentservice.exceptions;

public class BadRequestException extends RuntimeException {

    private final String errorMessage;

    public BadRequestException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
