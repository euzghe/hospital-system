package com.hospital.controller;

import com.hospital.model.PatientRecord;
import com.hospital.repo.PatientRecordRepository;
import com.hospital.repo.UserRepository;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/records")
public class RecordController {

    private final PatientRecordRepository records;
    private final UserRepository users;

    public RecordController(PatientRecordRepository records, UserRepository users) {
        this.records = records;
        this.users = users;
    }

    // Kayıt ekle
    @PostMapping
    public PatientRecord add(@RequestBody PatientRecord req){
        if (req.getDatetime()==null || req.getDatetime().isBlank())
            req.setDatetime(java.time.LocalDateTime.now().withNano(0).toString());
        return records.save(req);
    }

    // TC ile geçmiş
    @GetMapping("/patient/tc/{tcNo}")
    public List<PatientRecord> byTc(@PathVariable String tcNo){
        return records.findByTcNoOrderByDatetimeDesc(tcNo);
    }

    // Kullanıcı adına göre geçmiş (users.tc_no üzerinden)
    @GetMapping("/patient/{username}")
    public List<PatientRecord> byUsername(@PathVariable String username){
        return users.findByUsername(username)
                .map(u -> records.findByTcNoOrderByDatetimeDesc(u.getTcNo()))
                .orElseGet(List::of);
    }
}
