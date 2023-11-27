package com.sysvine.doctorappointmentservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class DoctorAppointmentConfig {

    @Bean
    public NamedParameterJdbcOperations jdbcOperations(final DataSource dataSource){
        return new NamedParameterJdbcTemplate(dataSource);
    }
}
