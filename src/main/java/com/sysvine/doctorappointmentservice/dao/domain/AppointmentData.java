package com.sysvine.doctorappointmentservice.dao.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class AppointmentData {

    private final String patientName;
    private final int doctorId;
    private final LocalDate appointmentDate;
    private final LocalTime appointmentTime;
    private final String appointmentId;
    private final Status status;

    public AppointmentData(Builder builder) {
        this.patientName = builder.patientName;
        this.doctorId = builder.doctorId;
        this.appointmentDate = builder.appointmentDate;
        this.appointmentTime = builder.appointmentTime;
        this.appointmentId = builder.appointmentId;
        this.status = builder.status;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getPatientName() {
        return patientName;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    public LocalTime getAppointmentTime() {
        return appointmentTime;
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public Status getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppointmentData that = (AppointmentData) o;
        return Objects.equals(patientName, that.patientName)
                && Objects.equals(doctorId, that.doctorId)
                && Objects.equals(appointmentDate, that.appointmentDate)
                && Objects.equals(appointmentTime, that.appointmentTime)
                && status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(patientName, doctorId, appointmentDate, appointmentTime, status);
    }

    @Override
    public String toString() {
        return "AppointmentData{" +
                "patientName='" + patientName + '\'' +
                ", doctorId='" + doctorId + '\'' +
                ", appointmentDate=" + appointmentDate +
                ", appointmentTime=" + appointmentTime +
                ", status=" + status +
                '}';
    }

    public static class Builder {
        private String patientName;
        private int doctorId;
        private LocalDate appointmentDate;
        private LocalTime appointmentTime;
        private String appointmentId;
        private Status status;

        public Builder withPatientName(final String thePatientName) {
            this.patientName = thePatientName;
            return this;
        }

        public Builder withDoctorId(final int theDoctorId) {
            this.doctorId = theDoctorId;
            return this;
        }

        public Builder withAppointmentDate(final LocalDate theAppointmentDate) {
            this.appointmentDate = theAppointmentDate;
            return this;
        }

        public Builder withAppointmentTime(final LocalTime theAppointmentTime) {
            this.appointmentTime = theAppointmentTime;
            return this;
        }

        public Builder withAppointmentId(final String theAppointmentId) {
            this.appointmentId = theAppointmentId;
            return this;
        }

        public Builder withStatus(final Status theStatus) {
            this.status = theStatus;
            return this;
        }

        public AppointmentData build() {
            return new AppointmentData(this);
        }
    }
}
