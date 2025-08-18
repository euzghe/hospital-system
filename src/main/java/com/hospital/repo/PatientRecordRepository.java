package com.hospital.repo;

import com.hospital.model.PatientRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PatientRecordRepository extends JpaRepository<PatientRecord, Long> {
    List<PatientRecord> findByTcNoOrderByDatetimeDesc(String tcNo);
    List<PatientRecord> findByPatientUsernameOrderByDatetimeDesc(String patientUsername);
}
