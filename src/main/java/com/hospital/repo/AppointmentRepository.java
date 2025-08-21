package com.hospital.repo;

import com.hospital.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findByDoctorUsernameOrderByDatetimeDesc(String doctorUsername);

    List<Appointment> findByPatientUsernameOrderByDatetimeDesc(String patientUsername);

    @Query("SELECT a FROM Appointment a " +
            "WHERE a.doctorUsername = :doctor " +
            "AND a.datetime LIKE CONCAT(:datePrefix, '%') " +
            "ORDER BY a.datetime")
    List<Appointment> findTodayForDoctor(@Param("doctor") String doctor,
                                         @Param("datePrefix") String datePrefix);
}
