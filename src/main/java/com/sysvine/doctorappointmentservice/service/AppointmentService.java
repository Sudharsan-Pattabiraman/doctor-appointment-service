package com.sysvine.doctorappointmentservice.service;

import com.sysvine.doctorappointmentservice.dto.AppointmentRequestDTO;
import com.sysvine.doctorappointmentservice.dto.AppointmentResponseDTO;
import com.sysvine.doctorappointmentservice.dto.UpdateAppointmentRequestDTO;

public interface AppointmentService {

    AppointmentResponseDTO newAppointment(final AppointmentRequestDTO requestDTO);

    String changeAppointment(final String appointmentId, final UpdateAppointmentRequestDTO requestDTO);

    AppointmentResponseDTO getAppointment(final String appointmentId);

    String cancelAppointment(final String appointmentId);

    void deleteAppointment(final String appointmentId);
}
