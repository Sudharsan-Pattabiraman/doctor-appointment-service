package com.sysvine.doctorappointmentservice.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;
import java.util.Optional;

import static com.sysvine.doctorappointmentservice.AppointmentUtils.*;

public class AppointmentRequestDTO {

    private final String patientName;
    private final String doctorName;
    private final LocalDate appointmentDate;
    private final LocalTime appointmentTime;

    private AppointmentRequestDTO(Builder builder) {
        this.patientName = validateString(builder.patientName, "patientName");
        this.doctorName = validateString(builder.doctorName, "doctorName");
        this.appointmentDate = validateDate(builder.appointmentDate, "appointmentDate");
        this.appointmentTime = validateTime(builder.appointmentTime, "appointmentTime");
    }

    @JsonCreator
    public static AppointmentRequestDTO create(
            @JsonProperty("patientName") final String thePatientName,
            @JsonProperty("doctorName") final String theDoctorName,
            @JsonProperty("appointmentDate") final LocalDate theAppointmentDate,
            @JsonProperty("appointmentTime") final LocalTime theAppointmentTime) {

        return builder()
                .withPatientName(thePatientName)
                .withDoctorName(theDoctorName)
                .withAppointmentDate(theAppointmentDate)
                .withAppointmentTime(theAppointmentTime)
                .build();
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getPatientName() {
        return patientName;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    public LocalTime getAppointmentTime() {
        return appointmentTime;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        AppointmentRequestDTO that = (AppointmentRequestDTO) o;
        return Objects.equals(patientName, that.patientName)
                && Objects.equals(doctorName, that.doctorName)
                && Objects.equals(appointmentDate, that.appointmentDate)
                && Objects.equals(appointmentTime, that.appointmentTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(patientName, doctorName, appointmentDate, appointmentTime);
    }

    @Override
    public String toString() {
        return "AppointmentRequestDTO {" +
                "patientName='" + patientName + '\'' +
                ", doctorName='" + doctorName + '\'' +
                ", appointmentDate=" + appointmentDate +
                ", appointmentTime=" + appointmentTime +
                '}';
    }

    private static class Builder {

        private Optional<String> patientName;
        private Optional<String> doctorName;
        private Optional<LocalDate> appointmentDate;
        private Optional<LocalTime> appointmentTime;

        public Builder withPatientName(final String thePatientName) {
            this.patientName = Optional.ofNullable(thePatientName);
            return this;
        }

        public Builder withDoctorName(final String theDoctorName) {
            this.doctorName = Optional.ofNullable(theDoctorName);
            return this;
        }

        public Builder withAppointmentDate(final LocalDate theAppointmentDate) {
            this.appointmentDate = Optional.ofNullable(theAppointmentDate);
            return this;
        }

        public Builder withAppointmentTime(final LocalTime theAppointmentTime) {
            this.appointmentTime = Optional.ofNullable(theAppointmentTime);
            return this;
        }

        public AppointmentRequestDTO build() {
            return new AppointmentRequestDTO(this);
        }
    }
}
