package com.hospital.controller;

import com.hospital.model.Doctor;
import com.hospital.repo.DoctorRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class DoctorController {

    private final DoctorRepository doctorRepo;

    public DoctorController(DoctorRepository doctorRepo) {
        this.doctorRepo = doctorRepo;
    }

    // Poliklinik listesini döndüren API uç noktası
    @GetMapping("/doctors/specialties")
    public List<String> getSpecialties() {
        return doctorRepo.distinctSpecialties();
    }

    // Tüm doktorları döndüren API uç noktası (örnek)
    @GetMapping("/doctors")
    public List<Doctor> getAllDoctors() {
        return doctorRepo.findAll();
    }
}

