package com.sysvine.doctorappointmentservice.exceptions;

public class ServerException extends RuntimeException{

    private final String errorMessage;

    public ServerException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
