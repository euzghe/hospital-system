package com.hospital.controller;

import com.hospital.model.Clinic;
import com.hospital.model.User;
import com.hospital.repo.ClinicRepository;
import com.hospital.repo.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/clinics")
public class ClinicController {

    private final ClinicRepository clinicRepo;
    private final UserRepository userRepo;

    public ClinicController(ClinicRepository clinicRepo, UserRepository userRepo) {
        this.clinicRepo = clinicRepo;
        this.userRepo = userRepo;
    }

    @GetMapping
    public List<Clinic> all() {
        return clinicRepo.findAll();
    }

    @GetMapping("/{clinic}/doctors")
    public List<Map<String, String>> doctorsByClinic(@PathVariable String clinic) {
        List<User> docs = userRepo.findByRoleAndClinicIgnoreCaseOrderByLastNameAsc("doctor", clinic);
        List<Map<String,String>> out = new ArrayList<>();
        for (User u : docs) {
            Map<String,String> m = new HashMap<>();
            m.put("username", u.getUsername());
            m.put("fullName", (opt(u.getFirstName()) + " " + opt(u.getLastName())).trim());
            out.add(m);
        }
        return out;
    }

    private String opt(String s){ return s==null ? "" : s; }
}
