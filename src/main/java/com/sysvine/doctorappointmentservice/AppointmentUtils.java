package com.sysvine.doctorappointmentservice;

import com.sysvine.doctorappointmentservice.exceptions.MissingFieldException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

public class AppointmentUtils {

    private static final String ERROR_MESSAGE = " must be present to make an appointment.";

    public static String validateString(final Optional<String> obj, final String fieldName) {

        return obj.map(str -> {
                    if (str.isEmpty()) {
                        throw new MissingFieldException(fieldName + ERROR_MESSAGE);
                    }
                    return str;
                })
                .orElseThrow(() -> new MissingFieldException(fieldName + ERROR_MESSAGE));
    }

    public static LocalDate validateDate(final Optional<LocalDate> obj, final String fieldName) {

        return obj.orElseThrow(() -> new MissingFieldException(fieldName + ERROR_MESSAGE));
    }

    public static LocalTime validateTime(final Optional<LocalTime> obj, final String fieldName) {

        return obj.orElseThrow(() -> new MissingFieldException(fieldName + ERROR_MESSAGE));
    }
}
