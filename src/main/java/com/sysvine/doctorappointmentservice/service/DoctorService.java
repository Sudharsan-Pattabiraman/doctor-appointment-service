package com.sysvine.doctorappointmentservice.service;

import com.sysvine.doctorappointmentservice.dao.domain.Doctor;

public interface DoctorService {

//    Doctor create(final DoctorRequestDTO)
    Doctor findByName(final String doctorName);
    Doctor findById(final int id);
}
