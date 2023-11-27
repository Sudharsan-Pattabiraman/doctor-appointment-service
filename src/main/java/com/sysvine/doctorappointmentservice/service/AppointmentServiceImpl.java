package com.sysvine.doctorappointmentservice.service;

import com.sysvine.doctorappointmentservice.dao.AppointmentDAO;
import com.sysvine.doctorappointmentservice.dao.DoctorDAO;
import com.sysvine.doctorappointmentservice.dao.domain.Appointment;
import com.sysvine.doctorappointmentservice.dao.domain.AppointmentData;
import com.sysvine.doctorappointmentservice.dao.domain.Doctor;
import com.sysvine.doctorappointmentservice.dao.domain.Status;
import com.sysvine.doctorappointmentservice.dto.AppointmentRequestDTO;
import com.sysvine.doctorappointmentservice.dto.AppointmentResponseDTO;
import com.sysvine.doctorappointmentservice.dto.UpdateAppointmentRequestDTO;
import com.sysvine.doctorappointmentservice.exceptions.BadRequestException;
import com.sysvine.doctorappointmentservice.exceptions.ServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppointmentServiceImpl.class);

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("DDD");
    private static int COUNTER = 0;

    private final DoctorDAO doctorDAO;
    private final AppointmentDAO appointmentDAO;

    public AppointmentServiceImpl(final DoctorDAO doctorDAO, final AppointmentDAO appointmentDAO) {
        this.doctorDAO = doctorDAO;
        this.appointmentDAO = appointmentDAO;
    }

    @Override
    public AppointmentResponseDTO newAppointment(final AppointmentRequestDTO requestDTO) {

        LOGGER.info("Patient: {} making new appointment on date: {} at time: {}",
                requestDTO.getPatientName(), requestDTO.getAppointmentDate(), requestDTO.getAppointmentTime());

        final Doctor doctor = doctorDAO.findByName(requestDTO.getDoctorName());

        validateDoctorTiming(requestDTO.getAppointmentTime(), doctor);

        List<Appointment> appointments =
                appointmentDAO.getByDoctorId(doctor.getId(), requestDTO.getAppointmentDate())
                        .stream()
                        .filter(appmnt ->
                                !(validateAppointmentDatetime(appmnt.getAppointmentTime(), requestDTO.getAppointmentTime())))
                        .collect(Collectors.toList());

        if (appointments.isEmpty()) {
            final Appointment appointment = appointmentDAO.createAppointment(buildAppointmentData(requestDTO, doctor));
            return buildAppointmentResponse(appointment);
        }

        LOGGER.error("Unable to make appointments for patientName: {}", requestDTO.getPatientName());
        throw new BadRequestException(String.format(
                "No slots open for appointments for doctor: %s on date: %s", requestDTO.getDoctorName(), requestDTO.getAppointmentDate()));
    }

    @Override
    public String changeAppointment(final String appointmentId, final UpdateAppointmentRequestDTO requestDTO) {

        final Appointment existingAppointment = validateAppointmentId(appointmentId);
        Doctor existingDoctor = doctorDAO.findById(existingAppointment.getDoctorId());

        final Optional<Doctor> updatedDoctor = requestDTO.getUpdatedDoctorName()
                .filter(name -> !name.equalsIgnoreCase(existingDoctor.getDoctorName()))
                .map(name -> updateDoctor(name, existingAppointment));

        requestDTO.getUpdatedAppointmentDate()
                .map(updatedDate -> {
                    if (updatedDate.isBefore(LocalDate.now())) {
                        throw new BadRequestException("Invalid date: ." + updatedDate);
                    }
                    return updatedDate;
                })
                .filter(updatedDate -> !updatedDate.isEqual(existingAppointment.getAppointmentDate()))
                .ifPresent(updatedDate -> {
                    LOGGER.info("Updating appointment date for appointmentId: {}", updatedDate);
                    int updatedRows = appointmentDAO.updateAppointmentDate(updatedDate, existingAppointment.getId());
                    if (updatedRows > 1) {
                        throw new ServerException("Multiple appointments updated for appointmentId: " + appointmentId);
                    }
                });

        requestDTO.getUpdatedAppointmentTime()
                .ifPresent(updatedTime -> updateAppointmentTime(updatedDoctor, existingDoctor, updatedTime, existingAppointment, requestDTO));

        return "Appointment updated successfully";
    }

    @Override
    public AppointmentResponseDTO getAppointment(final String appointmentId) {

        LOGGER.info("Fetching appointment for appointmentId: {}", appointmentId);
        final List<Appointment> appointments = appointmentDAO.getByAppointmentId(appointmentId);

        if (appointments.size() > 1) {
            throw new ServerException("Duplicate entries found for appointmentId: " + appointmentId);
        }

        return buildAppointmentResponse(appointments.get(0));
    }

    @Override
    public String cancelAppointment(final String appointmentId) {

        LOGGER.info("Cancelling appointment for appointmentId: {}", appointmentId);
        appointmentDAO.cancelAppointment(appointmentId);
        return "Appointment: " + appointmentId + " cancelled successfully.";
    }

    @Override
    public void deleteAppointment(String appointmentId) {

        LOGGER.info("Deleting appointment for appointmentId: {}", appointmentId);
        appointmentDAO.deleteAppointment(appointmentId);
    }

    private Doctor updateDoctor(final String name, final Appointment existingAppointment) {

        final Doctor newDoctor = doctorDAO.findByName(name);
        if (Objects.isNull(newDoctor)) {
            throw new BadRequestException("Invalid doctor name: " + name);
        }
        validateDoctorTiming(existingAppointment.getAppointmentTime(), newDoctor);
        LOGGER.info("Updating doctor for appointmentId: {}", existingAppointment.getAppointmentId());
        final int updatedRows = appointmentDAO.updateDoctor(newDoctor.getId(), existingAppointment.getId());
        if (updatedRows > 1) {
            throw new ServerException("Multiple appointments updated for appointmentId: " + existingAppointment.getAppointmentId());
        }
        return newDoctor;
    }

    private void updateAppointmentTime(
            final Optional<Doctor> updatedDoctor,
            final Doctor existingDoctor,
            final LocalTime updatedTime,
            final Appointment existingAppointment,
            final UpdateAppointmentRequestDTO requestDTO) {

        validateDoctorTiming(updatedTime, updatedDoctor.orElse(existingDoctor));

        if (!updatedTime.equals(existingAppointment.getAppointmentTime())) {

            List<Appointment> appointments =
                    getAllAppointments(
                            updatedDoctor.map(Doctor::getId).orElse(existingDoctor.getId()),
                            requestDTO.getUpdatedAppointmentDate().orElse(existingAppointment.getAppointmentDate()),
                            updatedTime);

            if (appointments.isEmpty()) {
                final int updatedRows = appointmentDAO.updateAppointmentTime(updatedTime, existingAppointment.getId());
                if (updatedRows > 1) {
                    throw new ServerException("Multiple appointments updated for appointmentId: " + existingAppointment.getAppointmentId());
                }
            } else {
                throw new ServerException("Unable to update the appointmentTime to ." + updatedTime);
            }
        }
    }

    private Appointment validateAppointmentId(final String appointmentId) {

        List<Appointment> appointments = appointmentDAO.getByAppointmentId(appointmentId);

        if (appointments.isEmpty()) {
            throw new BadRequestException("Invalid appointmentId: " + appointmentId);
        }

        if (appointments.size() > 1) {
            throw new ServerException("Duplicate entries found for appointmentId: " + appointmentId);
        }

        return appointments.get(0);
    }

    private List<Appointment> getAllAppointments(
            final int id,
            final LocalDate appointmentDate,
            final LocalTime appointmentTime) {

        return appointmentDAO.getByDoctorId(id, appointmentDate)
                .stream()
                .filter(appmnt ->
                        !(validateAppointmentDatetime(appmnt.getAppointmentTime(), appointmentTime)))
                .collect(Collectors.toList());
    }

    private AppointmentResponseDTO buildAppointmentResponse(final Appointment appointment) {

        return AppointmentResponseDTO.builder()
                .withPatientName(appointment.getPatientName())
                .withAppointmentId(appointment.getAppointmentId())
                .withAppointmentDate(appointment.getAppointmentDate())
                .withAppointmentTime(appointment.getAppointmentTime())
                .build();
    }

    private AppointmentData buildAppointmentData(final AppointmentRequestDTO requestDTO, final Doctor doctor) {

        return AppointmentData.builder()
                .withPatientName(requestDTO.getPatientName())
                .withDoctorId(doctor.getId())
                .withAppointmentDate(requestDTO.getAppointmentDate())
                .withAppointmentTime(requestDTO.getAppointmentTime())
                .withAppointmentId(buildAppointmentId(doctor.getId()))
                .withStatus(Status.SUCCESS)
                .build();
    }

    private void validateDoctorTiming(final LocalTime appointmentTime, final Doctor doctor) {

        if (appointmentTime.isBefore(doctor.getAvailableFrom())
                || appointmentTime.isAfter(doctor.getAvailableTill())) {

            final String errorMessage = String.format("Doctor %s is available from: %s till: %s",
                    doctor.getDoctorName(),
                    doctor.getAvailableFrom().format(TIME_FORMATTER),
                    doctor.getAvailableTill().format(TIME_FORMATTER));
            throw new BadRequestException(errorMessage);
        }
    }

    private boolean validateAppointmentDatetime(
            final LocalTime existingAppointmentTime,
            final LocalTime appointmentTime) {

        return (appointmentTime.compareTo(existingAppointmentTime) > 0
                && existingAppointmentTime.plusMinutes(15L).isBefore(appointmentTime))
                || (appointmentTime.compareTo(existingAppointmentTime) == -1
                && existingAppointmentTime.minusMinutes(15L).isAfter(appointmentTime));
    }

    private String buildAppointmentId(final int doctorId) {

        final String timestamp = String.valueOf(System.currentTimeMillis()).substring(0, 5);
        final String appointmentId = "APPMNT-" + doctorId + timestamp + COUNTER;
        COUNTER++;
        return appointmentId;
    }
}
