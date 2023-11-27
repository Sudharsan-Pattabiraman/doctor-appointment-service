package com.sysvine.doctorappointmentservice.dao.domain;

import java.time.LocalTime;
import java.util.Objects;

public class Doctor {

    private final int id;
    private final String doctorName;
    private final LocalTime availableFrom;
    private final LocalTime availableTill;

    public Doctor(Builder builder) {
        this.id = builder.id;
        this.doctorName = builder.doctorName;
        this.availableFrom = builder.availableFrom;
        this.availableTill = builder.availableTill;
    }

    public static Builder builder() {
        return new Builder();
    }

    public int getId() {
        return id;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public LocalTime getAvailableFrom() {
        return availableFrom;
    }

    public LocalTime getAvailableTill() {
        return availableTill;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Doctor doctor = (Doctor) o;
        return id == doctor.id
                && Objects.equals(doctorName, doctor.doctorName)
                && Objects.equals(availableFrom, doctor.availableFrom)
                && Objects.equals(availableTill, doctor.availableTill);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, doctorName, availableFrom, availableTill);
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "id=" + id +
                ", doctorName='" + doctorName + '\'' +
                ", availableFrom=" + availableFrom +
                ", availableTill=" + availableTill +
                '}';
    }

    public static class Builder {

        private int id;
        private String doctorName;
        private LocalTime availableFrom;
        private LocalTime availableTill;

        public Builder withId(final int theId) {
            this.id = theId;
            return this;
        }

        public Builder withDoctorName(final String theDoctorName) {
            this.doctorName = theDoctorName;
            return this;
        }

        public Builder withAvailableFrom(final LocalTime theAvailableFrom) {
            this.availableFrom = theAvailableFrom;
            return this;
        }

        public Builder withAvailableTill(final LocalTime theAvailableTill) {
            this.availableTill = theAvailableTill;
            return this;
        }

        public Doctor build() {
            return new Doctor(this);
        }
    }
}
