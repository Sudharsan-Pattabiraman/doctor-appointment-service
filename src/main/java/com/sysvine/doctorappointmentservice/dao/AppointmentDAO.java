package com.sysvine.doctorappointmentservice.dao;

import com.sysvine.doctorappointmentservice.dao.domain.Appointment;
import com.sysvine.doctorappointmentservice.dao.domain.AppointmentData;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface AppointmentDAO {

    Appointment createAppointment(final AppointmentData data);

    List<Appointment> getByAppointmentId(final String appointmentId);

    List<Appointment> getByDoctorId(final int doctorId, final LocalDate appointmentDate);

    int updateDoctor(final int doctorId, final int id);

    int updateAppointmentDate(final LocalDate updatedDate, final int id);

    int updateAppointmentTime(final LocalTime updatedTime, final int id);

    void cancelAppointment(final String appointmentId);

    void deleteAppointment(final String appointmentId);
}
