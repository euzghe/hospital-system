package com.hospital.controller;

import com.hospital.model.Appointment;
import com.hospital.repo.AppointmentRepository;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentRepository repo;

    public AppointmentController(AppointmentRepository repo) {
        this.repo = repo;
    }

    @PostMapping
    public Appointment create(@RequestBody Appointment req){
        if (req.getStatus()==null || req.getStatus().isBlank()) req.setStatus("scheduled");
        if (req.getCreatedAt()==null || req.getCreatedAt().isBlank())
            req.setCreatedAt(java.time.LocalDateTime.now().withNano(0).toString());
        return repo.save(req);
    }

    @GetMapping("/patient/{username}")
    public List<Appointment> byPatient(@PathVariable String username){
        return repo.findByPatientUsernameOrderByDatetimeDesc(username);
    }

    @GetMapping("/doctor/{username}")
    public List<Appointment> byDoctor(@PathVariable String username){
        return repo.findByDoctorUsernameOrderByDatetimeDesc(username);
    }

    @PutMapping("/{id}/status")
    public Map<String,String> updateStatus(@PathVariable Long id, @RequestBody Map<String,String> body){
        return repo.findById(id).map(a -> {
            a.setStatus(body.getOrDefault("status","scheduled"));
            repo.save(a);
            return Map.of("status","ok");
        }).orElse(Map.of("status","error","message","Randevu bulunamadı"));
    }
}
