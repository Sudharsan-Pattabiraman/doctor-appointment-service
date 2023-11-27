package com.sysvine.doctorappointmentservice.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;
import java.util.Optional;

public class UpdateAppointmentRequestDTO {

    private final Optional<LocalDate> updatedAppointmentDate;
    private final Optional<LocalTime> updatedAppointmentTime;
    private final Optional<String> updatedDoctorName;

    private UpdateAppointmentRequestDTO(Builder builder) {
        this.updatedDoctorName = builder.updatedDoctorName;
        this.updatedAppointmentDate = builder.updatedAppointmentDate;
        this.updatedAppointmentTime = builder.updatedAppointmentTime;
    }

    @JsonCreator
    public static UpdateAppointmentRequestDTO create(
            @JsonProperty("doctorName") final String theUpdatedDoctorName,
            @JsonProperty("appointmentDate") final LocalDate theAppointmentDate,
            @JsonProperty("appointmentTime") final LocalTime theAppointmentTime) {

        return builder()
                .withUpdatedDoctorName(theUpdatedDoctorName)
                .withUpdatedAppointmentDate(theAppointmentDate)
                .withUpdatedAppointmentTime(theAppointmentTime)
                .build();
    }

    public static Builder builder(){
        return new Builder();
    }

    public Optional<LocalDate> getUpdatedAppointmentDate() {
        return updatedAppointmentDate;
    }

    public Optional<LocalTime> getUpdatedAppointmentTime() {
        return updatedAppointmentTime;
    }

    public Optional<String> getUpdatedDoctorName() {
        return updatedDoctorName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UpdateAppointmentRequestDTO that = (UpdateAppointmentRequestDTO) o;
        return Objects.equals(updatedAppointmentDate, that.updatedAppointmentDate)
                && Objects.equals(updatedAppointmentTime, that.updatedAppointmentTime)
                && Objects.equals(updatedDoctorName, that.updatedDoctorName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(updatedAppointmentDate, updatedAppointmentTime, updatedDoctorName);
    }

    @Override
    public String toString() {
        return "UpdateAppointmentRequestDTO{" +
                "updatedAppointmentDate=" + updatedAppointmentDate +
                ", updatedAppointmentTime=" + updatedAppointmentTime +
                ", updatedDoctorName=" + updatedDoctorName +
                '}';
    }

    private static class Builder {

        private Optional<String> updatedDoctorName;
        private Optional<LocalDate> updatedAppointmentDate;
        private Optional<LocalTime> updatedAppointmentTime;

        public Builder withUpdatedDoctorName(final String theUpdatedDoctorName) {
            this.updatedDoctorName = Optional.ofNullable(theUpdatedDoctorName);
            return this;
        }

        public Builder withUpdatedAppointmentDate(final LocalDate theUpdatedAppointmentDate) {
            this.updatedAppointmentDate = Optional.ofNullable(theUpdatedAppointmentDate);
            return this;
        }

        public Builder withUpdatedAppointmentTime(final LocalTime theUpdatedAppointmentTime) {
            this.updatedAppointmentTime = Optional.ofNullable(theUpdatedAppointmentTime);
            return this;
        }

        public UpdateAppointmentRequestDTO build() {
            return new UpdateAppointmentRequestDTO(this);
        }
    }
}
