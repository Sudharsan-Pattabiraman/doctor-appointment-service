package com.sysvine.doctorappointmentservice.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetTime;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

import static com.sysvine.doctorappointmentservice.AppointmentUtils.*;

public class AppointmentResponseDTO {

    private final String patientName;
    private final String appointmentId;
    private final LocalDate appointmentDate;
    private final LocalTime appointmentTime;

    private AppointmentResponseDTO(Builder builder) {
        this.patientName = validateString(builder.patientName, "patientName");
        this.appointmentId = validateString(builder.appointmentId, "appointmentId");
        this.appointmentDate = validateDate(builder.appointmentDate, "appointmentDate");
        this.appointmentTime = validateTime(builder.appointmentTime, "appointmentTime");
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getPatientName() {
        return patientName;
    }

    public String getAppointmentId() {
        return appointmentId;
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
        AppointmentResponseDTO that = (AppointmentResponseDTO) o;
        return Objects.equals(patientName, that.patientName) 
                && Objects.equals(appointmentId, that.appointmentId) 
                && Objects.equals(appointmentDate, that.appointmentDate) 
                && Objects.equals(appointmentTime, that.appointmentTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(patientName, appointmentId, appointmentDate, appointmentTime);
    }

    @Override
    public String toString() {
        return "AppointmentResponseDTO{" +
                "patientName='" + patientName + '\'' +
                ", appointmentId='" + appointmentId + '\'' +
                ", appointmentDate=" + appointmentDate +
                ", appointmentTime=" + appointmentTime +
                '}';
    }

    public static class Builder {

        private Optional<String> patientName;
        private Optional<String> appointmentId;
        private Optional<LocalDate> appointmentDate;
        private Optional<LocalTime> appointmentTime;

        public Builder withPatientName(final String thePatientName) {
            this.patientName = Optional.ofNullable(thePatientName);
            return this;
        }

        public Builder withAppointmentId(final String theAppointmentId) {
            this.appointmentId = Optional.ofNullable(theAppointmentId);
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

        public AppointmentResponseDTO build() {
            return new AppointmentResponseDTO(this);
        }
    }
}
