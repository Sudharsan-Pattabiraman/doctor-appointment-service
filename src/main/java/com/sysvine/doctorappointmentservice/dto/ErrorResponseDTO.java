package com.sysvine.doctorappointmentservice.dto;

import java.util.Objects;

public class ErrorResponseDTO {

    private final ErrorCode errorCode;
    private final String errorMessage;

    public ErrorResponseDTO(Builder builder) {
        this.errorCode = builder.errorCode;
        this.errorMessage = builder.errorMessage;
    }

    public static Builder builder() {
        return new Builder();
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ErrorResponseDTO that = (ErrorResponseDTO) o;
        return errorCode == that.errorCode && Objects.equals(errorMessage, that.errorMessage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(errorCode, errorMessage);
    }

    @Override
    public String toString() {
        return "ErrorResponseDTO{" +
                "errorCode=" + errorCode +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }

    public enum ErrorCode {
        BAD_REQUEST, SERVER_ERROR
    }

    public static class Builder {

        private ErrorCode errorCode;
        private String errorMessage;

        public Builder withErrorCode(final ErrorCode theErrorCode) {
            this.errorCode = theErrorCode;
            return this;
        }

        public Builder withErrorMessage(final String theErrorMessage) {
            this.errorMessage = theErrorMessage;
            return this;
        }

        public ErrorResponseDTO build() {
            return new ErrorResponseDTO(this);
        }
    }
}
