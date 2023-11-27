package com.sysvine.doctorappointmentservice.service;

import com.sysvine.doctorappointmentservice.dao.DoctorDAO;
import com.sysvine.doctorappointmentservice.dao.domain.Doctor;
import org.springframework.stereotype.Service;

@Service
public class DoctorServiceImpl implements DoctorService {

    private final DoctorDAO doctorDAO;

    public DoctorServiceImpl(DoctorDAO doctorDAO) {
        this.doctorDAO = doctorDAO;
    }

    @Override
    public Doctor findByName(String doctorName) {

        return doctorDAO.findByName(doctorName);
    }

    @Override
    public Doctor findById(int id) {
        return null;
    }
}
