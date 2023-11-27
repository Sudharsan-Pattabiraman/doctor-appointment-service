package com.sysvine.doctorappointmentservice.dao.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

public class Appointment {

    private final int id;
    private final String patientName;
    private final int doctorId;
    private final LocalDate appointmentDate;
    private final LocalTime appointmentTime;
    private final String appointmentId;
    private final Status status;
    private final LocalDateTime createdDatetime;

    public Appointment(Builder builder) {
        this.id = builder.id;
        this.patientName = builder.patientName;
        this.doctorId = builder.doctorId;
        this.appointmentDate = builder.appointmentDate;
        this.appointmentTime = builder.appointmentTime;
        this.appointmentId = builder.appointmentId;
        this.status = builder.status;
        this.createdDatetime = builder.createdDatetime;
    }

    public static Builder builder(){
        return new Builder();
    }

    public int getId() {
        return id;
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

    public LocalDateTime getCreatedDatetime() {
        return createdDatetime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Appointment that = (Appointment) o;
        return id == that.id
                && doctorId == that.doctorId
                && Objects.equals(patientName, that.patientName)
                && Objects.equals(appointmentDate, that.appointmentDate)
                && Objects.equals(appointmentTime, that.appointmentTime)
                && Objects.equals(appointmentId, that.appointmentId)
                && status == that.status
                && Objects.equals(createdDatetime, that.createdDatetime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                id,
                patientName,
                doctorId,
                appointmentDate,
                appointmentTime,
                appointmentId,
                status,
                createdDatetime);
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "id=" + id +
                ", patientName='" + patientName + '\'' +
                ", doctorId=" + doctorId +
                ", appointmentDate=" + appointmentDate +
                ", appointmentTime=" + appointmentTime +
                ", appointmentId='" + appointmentId + '\'' +
                ", status=" + status +
                ", createdDatetime=" + createdDatetime +
                '}';
    }

    public static class Builder {
        private int id;
        private String patientName;
        private int doctorId;
        private LocalDate appointmentDate;
        private LocalTime appointmentTime;
        private String appointmentId;
        private Status status;
        private LocalDateTime createdDatetime;
        
        public Builder withId(final int theId){
            this.id = theId;
            return this;
        }
        
        public Builder withPatientName(final String thePatientName){
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

        public Builder withCreatedDatetime(final LocalDateTime theCreatedDatetime){
            this.createdDatetime = theCreatedDatetime;
            return this;
        }

        public Appointment build() {
            return new Appointment(this);
        }
    }
}
