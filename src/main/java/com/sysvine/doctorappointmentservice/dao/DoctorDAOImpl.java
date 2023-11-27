package com.sysvine.doctorappointmentservice.dao;

import com.sysvine.doctorappointmentservice.dao.domain.Doctor;
import com.sysvine.doctorappointmentservice.dao.domain.DoctorData;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.simple.SimpleJdbcInsertOperations;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;

@Repository
public class DoctorDAOImpl implements DoctorDAO {

    private static final String GET_DOCTOR_BY_NAME_QUERY =
            "SELECT id, doctor_name, available_from, available_till FROM doctor WHERE doctor_name = :doctorName";
    private static final String GET_DOCTOR_BY_ID_QUERY =
            "SELECT id, doctor_name, available_from, available_till FROM doctor WHERE id = :id";

    private final NamedParameterJdbcOperations jdbcOperations;

    public DoctorDAOImpl(final NamedParameterJdbcOperations jdbcOperations) {

        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public Doctor create(final DoctorData doctorData) {
        return null;
    }

    @Override
    public Doctor findByName(String doctorName) {

        return jdbcOperations.queryForObject(
                GET_DOCTOR_BY_NAME_QUERY,
                new MapSqlParameterSource("doctorName", doctorName),
                rowMapper());
    }

    @Override
    public Doctor findById(int id) {

        return jdbcOperations.queryForObject(
                GET_DOCTOR_BY_ID_QUERY,
                new MapSqlParameterSource("id", id),
                rowMapper());
    }

    private RowMapper<Doctor> rowMapper() {

        return (rs, rowNum) -> Doctor.builder()
                .withId(rs.getInt("id"))
                .withDoctorName(rs.getString("doctor_name"))
                .withAvailableFrom(LocalTime.parse(rs.getString("available_from")))
                .withAvailableTill(LocalTime.parse(rs.getString("available_till")))
                .build();
    }
}
