package com.sysvine.doctorappointmentservice.dao;

import com.sysvine.doctorappointmentservice.dao.domain.Doctor;
import com.sysvine.doctorappointmentservice.dao.domain.DoctorData;

import javax.print.Doc;

public interface DoctorDAO {

    Doctor create(final DoctorData doctorData);
    Doctor findByName(final String doctorName);
    Doctor findById(final int id);
}
