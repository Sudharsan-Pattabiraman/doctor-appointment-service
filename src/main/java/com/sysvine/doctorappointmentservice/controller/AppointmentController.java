package com.sysvine.doctorappointmentservice.controller;

import com.sysvine.doctorappointmentservice.AppointmentUtils;
import com.sysvine.doctorappointmentservice.dto.AppointmentRequestDTO;
import com.sysvine.doctorappointmentservice.dto.AppointmentResponseDTO;
import com.sysvine.doctorappointmentservice.dto.ErrorResponseDTO;
import com.sysvine.doctorappointmentservice.dto.UpdateAppointmentRequestDTO;
import com.sysvine.doctorappointmentservice.exceptions.BadRequestException;
import com.sysvine.doctorappointmentservice.exceptions.ServerException;
import com.sysvine.doctorappointmentservice.service.AppointmentService;
import com.sysvine.doctorappointmentservice.service.AppointmentServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class AppointmentController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppointmentController.class);

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping(
            value = "/appointments",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AppointmentResponseDTO> makeAppointment(@RequestBody final AppointmentRequestDTO requestDTO) {

        return ResponseEntity.status(HttpStatus.CREATED).body(appointmentService.newAppointment(requestDTO));
    }

    @PutMapping(
            value = "/appointments/{appmntId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateAppointment(
            @PathVariable("appmntId") final String appointmentId,
            @RequestBody final UpdateAppointmentRequestDTO requestDTO) {

        AppointmentUtils.validateString(Optional.ofNullable(appointmentId), "appointmentId");

        return ResponseEntity.ok(appointmentService.changeAppointment(appointmentId, requestDTO));
    }

    @PutMapping(value = "/appointments/{appmntId}/cancel")
    public ResponseEntity<String> cancelAppointment(@PathVariable("appmntId") final String appointmentId) {

        AppointmentUtils.validateString(Optional.ofNullable(appointmentId), "appointmentId");
        return ResponseEntity.ok(appointmentService.cancelAppointment(appointmentId));
    }

    @GetMapping(value = "/appointments/{appmntId}")
    public ResponseEntity<AppointmentResponseDTO> getAppointment(@PathVariable("appmntId") final String appointmentId) {

        AppointmentUtils.validateString(Optional.ofNullable(appointmentId), "appointmentId");
        return ResponseEntity.ok(appointmentService.getAppointment(appointmentId));
    }

    @DeleteMapping(value = "/appointments/{appmntId}")
    public void deleteAppointment(@PathVariable("appmntId") final String appointmentId) {

        AppointmentUtils.validateString(Optional.ofNullable(appointmentId), "appointmentId");
        appointmentService.deleteAppointment(appointmentId);
    }

    @ExceptionHandler(value = BadRequestException.class)
    public ResponseEntity<ErrorResponseDTO> handleBadRequestException(final BadRequestException exception) {

        final ErrorResponseDTO errorResponseDTO = ErrorResponseDTO.builder()
                .withErrorCode(ErrorResponseDTO.ErrorCode.BAD_REQUEST)
                .withErrorMessage(exception.getErrorMessage())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponseDTO);
    }

    @ExceptionHandler(value = ServerException.class)
    public ResponseEntity<ErrorResponseDTO> handleServerException(final ServerException exception) {

        final ErrorResponseDTO errorResponseDTO = ErrorResponseDTO.builder()
                .withErrorCode(ErrorResponseDTO.ErrorCode.SERVER_ERROR)
                .withErrorMessage(exception.getErrorMessage())
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponseDTO);
    }

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<ErrorResponseDTO> handleRuntimeException(final RuntimeException exception) {


        final ErrorResponseDTO errorResponseDTO = ErrorResponseDTO.builder()
                .withErrorCode(ErrorResponseDTO.ErrorCode.SERVER_ERROR)
                .withErrorMessage(exception.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponseDTO);
    }
}
