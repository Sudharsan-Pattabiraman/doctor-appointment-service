package com.sysvine.doctorappointmentservice.dao;

import com.sysvine.doctorappointmentservice.dao.domain.Appointment;
import com.sysvine.doctorappointmentservice.dao.domain.AppointmentData;
import com.sysvine.doctorappointmentservice.dao.domain.Status;
import com.sysvine.doctorappointmentservice.exceptions.ServerException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Types;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Repository
public class AppointmentDAOImpl implements AppointmentDAO {

    private static final String GET_APPOINTMENTS_BY_DOCTOR_ID_QUERY =
            "SELECT * FROM appointment WHERE doctor_id = :doctorId AND appointment_date = :appointmentDate";
    private static final String GET_APPOINTMENT_BY_APPOINTMENT_ID_QUERY =
            "SELECT * FROM appointment WHERE appointment_id = :appointmentId AND status = 'SUCCESS'";
    private static final String CANCEL_APPOINTMENT_QUERY = "UPDATE appointment SET status = 'CANCELLED' WHERE appointment_id = :appointmentId AND status = 'SUCCESS'";
    private static final String DELETE_APPOINTMENT_QUERY = "DELETE FROM appointment WHERE appointment_id = :appointmentId";

    private final DataSource dataSource;
    private final NamedParameterJdbcOperations jdbcOperations;

    public AppointmentDAOImpl(
            final DataSource dataSource,
            final NamedParameterJdbcOperations jdbcOperations) {

        this.dataSource = dataSource;
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public Appointment createAppointment(final AppointmentData appointmentData) {

        final LocalDateTime createdDateTime = LocalDateTime.now();

        final MapSqlParameterSource appointmentDataParameterSource = new MapSqlParameterSource();
        appointmentDataParameterSource.addValue("patient_name", appointmentData.getPatientName(), Types.VARCHAR);
        appointmentDataParameterSource.addValue("doctor_id", appointmentData.getDoctorId(), Types.INTEGER);
        appointmentDataParameterSource.addValue("appointment_date", appointmentData.getAppointmentDate(), Types.DATE);
        appointmentDataParameterSource.addValue("appointment_time", appointmentData.getAppointmentTime(), Types.TIME);
        appointmentDataParameterSource.addValue("appointment_id", appointmentData.getAppointmentId(), Types.VARCHAR);
        appointmentDataParameterSource.addValue("status", appointmentData.getStatus().name(), Types.VARCHAR);
        appointmentDataParameterSource.addValue("created_datetime", createdDateTime, Types.TIMESTAMP);

        final Number generatedId = new SimpleJdbcInsert(dataSource).withTableName("appointment")
                .usingGeneratedKeyColumns("id")
                .executeAndReturnKey(appointmentDataParameterSource);

        return buildAppointment(generatedId.intValue(), createdDateTime, appointmentData);
    }

    @Override
    public List<Appointment> getByAppointmentId(String appointmentId) {

        return jdbcOperations.query(
                GET_APPOINTMENT_BY_APPOINTMENT_ID_QUERY,
                new MapSqlParameterSource("appointmentId", appointmentId),
                appointmentRowMapper());
    }

    @Override
    public List<Appointment> getByDoctorId(final int doctorId, final LocalDate appointmentDate) {

        final SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("doctorId", doctorId)
                .addValue("appointmentDate", appointmentDate);

        return jdbcOperations.query(GET_APPOINTMENTS_BY_DOCTOR_ID_QUERY, namedParameters, appointmentRowMapper());
    }

    @Override
    public int updateDoctor(final int doctorId, final int id) {

        final String updateDoctorIdQuery = "UPDATE appointment SET doctor_id = :doctorId WHERE id = :id AND status = 'SUCCESS'";
        final MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", id)
                .addValue("doctorId", doctorId);

        return jdbcOperations.update(updateDoctorIdQuery, parameterSource);
    }

    @Override
    public int updateAppointmentDate(LocalDate updatedDate, int id) {

        final String updateAppointmentDateQuery =
                "UPDATE appointment SET appointment_date = :appointmentDate WHERE id = :id AND status = 'SUCCESS'";
        final MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", id)
                .addValue("appointmentDate", updatedDate, Types.DATE);

        return jdbcOperations.update(updateAppointmentDateQuery, parameterSource);
    }

    @Override
    public int updateAppointmentTime(LocalTime updatedTime, int id) {

        final String updateAppointmentDateQuery =
                "UPDATE appointment SET appointment_time = :appointmentTime WHERE id = :id AND status = 'SUCCESS'";
        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", id)
                .addValue("appointmentTime", updatedTime, Types.TIME);

        return jdbcOperations.update(updateAppointmentDateQuery, parameterSource);
    }

    @Override
    public void cancelAppointment(final String appointmentId) {

        final int rowsAffected = jdbcOperations.update(
                CANCEL_APPOINTMENT_QUERY, new MapSqlParameterSource("appointmentId", appointmentId));

        if (rowsAffected != 1) {
            throw new ServerException("No/Multiple appointments cancelled for appointmentId: ." + appointmentId);
        }
    }

    @Override
    public void deleteAppointment(String appointmentId) {

        final int rowsAffected = jdbcOperations.update(
                DELETE_APPOINTMENT_QUERY, new MapSqlParameterSource("appointmentId", appointmentId));

        if (rowsAffected > 1) {
            throw new ServerException("Multiple appointments deleted for appointmentId: ." + appointmentId);
        }
    }

    private Appointment buildAppointment(
            final int id,
            final LocalDateTime createdDateTime,
            final AppointmentData appointmentData) {

        return Appointment.builder()
                .withId(id)
                .withPatientName(appointmentData.getPatientName())
                .withDoctorId(appointmentData.getDoctorId())
                .withAppointmentDate(appointmentData.getAppointmentDate())
                .withAppointmentTime(appointmentData.getAppointmentTime())
                .withAppointmentId(appointmentData.getAppointmentId())
                .withStatus(appointmentData.getStatus())
                .withCreatedDatetime(createdDateTime)
                .build();
    }

    private RowMapper<Appointment> appointmentRowMapper() {

        return (rs, rowNum) -> Appointment.builder()
                .withId(rs.getInt("id"))
                .withPatientName(rs.getString("patient_name"))
                .withDoctorId(rs.getInt("doctor_id"))
                .withAppointmentDate(rs.getDate("appointment_date").toLocalDate())
                .withAppointmentTime(rs.getTime("appointment_time").toLocalTime())
                .withAppointmentId(rs.getString("appointment_id"))
                .withStatus(Status.valueOf(rs.getString("status")))
                .withCreatedDatetime(rs.getTimestamp("created_datetime").toLocalDateTime())
                .build();
    }
}
