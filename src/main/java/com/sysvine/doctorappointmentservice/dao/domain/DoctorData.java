package com.sysvine.doctorappointmentservice.dao.domain;

import java.time.LocalTime;
import java.util.Objects;

public class DoctorData {

    private final String doctorName;
    private final LocalTime availableFrom;
    private final LocalTime availableTill;

    public DoctorData(Builder builder) {
        this.doctorName = builder.doctorName;
        this.availableFrom = builder.availableFrom;
        this.availableTill = builder.availableTill;
    }

    public static Builder builder() {
        return new Builder();
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
        DoctorData that = (DoctorData) o;
        return Objects.equals(doctorName, that.doctorName)
                && Objects.equals(availableFrom, that.availableFrom)
                && Objects.equals(availableTill, that.availableTill);
    }

    @Override
    public int hashCode() {
        return Objects.hash( doctorName, availableFrom, availableTill);
    }

    @Override
    public String toString() {
        return "Doctor{" +
                " doctorName='" + doctorName + '\'' +
                ", availableFrom=" + availableFrom +
                ", availableTill=" + availableTill +
                '}';
    }

    public static class Builder {

        private String doctorName;
        private LocalTime availableFrom;
        private LocalTime availableTill;

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

        public DoctorData build() {
            return new DoctorData(this);
        }
    }
}