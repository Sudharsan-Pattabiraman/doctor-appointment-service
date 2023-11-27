package com.sysvine.doctorappointmentservice.exceptions;

public class MissingFieldException extends RuntimeException {

    private final String errorMessage;

    public MissingFieldException(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage(){
        return this.errorMessage;
    }
}
