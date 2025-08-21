package com.hospital.controller;

import com.hospital.model.Appointment;
import com.hospital.model.PatientRecord;
import com.hospital.repo.AppointmentRepository;
import com.hospital.repo.DoctorRepository;
import com.hospital.repo.PatientRecordRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
public class AppointmentController {

    private final AppointmentRepository apptRepo;
    private final PatientRecordRepository recordRepo;
    private final DoctorRepository doctorRepo;

    public AppointmentController(AppointmentRepository apptRepo,
                                 PatientRecordRepository recordRepo,
                                 DoctorRepository doctorRepo) {
        this.apptRepo = apptRepo;
        this.recordRepo = recordRepo;
        this.doctorRepo = doctorRepo;
    }

    // --- CREATE ---
    @PostMapping("/appointments")
    public Appointment create(@RequestBody Appointment a) {
        // Klinik boş ise doktorun branşıyla doldur
        if (a.getClinic() == null || a.getClinic().isBlank()) {
            doctorRepo.findByUsername(a.getDoctorUsername())
                    .ifPresent(d -> a.setClinic(d.getSpecialty()));
        }
        if (a.getStatus() == null || a.getStatus().isBlank()) {
            a.setStatus("scheduled");
        }
        if (a.getCreatedAt() == null || a.getCreatedAt().isBlank()) {
            a.setCreatedAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")));
        }
        return apptRepo.save(a);
    }

    // --- LISTS ---
    @GetMapping("/appointments/doctor/{username}")
    public List<Appointment> byDoctor(@PathVariable String username) {
        return apptRepo.findByDoctorUsernameOrderByDatetimeDesc(username);
    }

    @GetMapping("/appointments/doctor/{username}/today")
    public List<Appointment> todayForDoctor(@PathVariable String username) {
        String prefix = LocalDate.now().toString(); // YYYY-MM-DD
        return apptRepo.findTodayForDoctor(username, prefix);
    }

    @GetMapping("/appointments/patient/{username}")
    public List<Appointment> byPatient(@PathVariable String username) {
        return apptRepo.findByPatientUsernameOrderByDatetimeDesc(username);
    }

    // --- COMPLETE VISIT ---
    public static class CompletePayload {
        public String diagnosis;
        public String findings;
        public String labs;
        public String notes;
    }

    @PostMapping("/appointments/{id}/complete")
    public Map<String,Object> complete(@PathVariable Long id,
                                       @RequestBody CompletePayload body) {
        Map<String,Object> res = new HashMap<>();
        var opt = apptRepo.findById(id);
        if (opt.isEmpty()) {
            res.put("status","error");
            res.put("message","Appointment not found");
            return res;
        }
        var a = opt.get();

        // hasta geçmişi kaydı
        PatientRecord r = new PatientRecord();
        r.setDoctorUsername(a.getDoctorUsername());
        r.setPatientUsername(a.getPatientUsername());
        r.setDatetime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")));
        r.setDiagnosis(body.diagnosis);
        r.setFindings(body.findings);
        r.setLabs(body.labs);
        r.setNotes(body.notes);
        r.setCreatedAt(r.getDatetime());
        recordRepo.save(r);

        // randevuyu kapat
        a.setStatus("done");
        apptRepo.save(a);

        res.put("status","ok");
        res.put("recordId", r.getId());
        return res;
    }
}
