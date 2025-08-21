package com.hospital.repo;

import com.hospital.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional; // Optional sınıfını ekledik

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    @Query("select distinct d.specialty from Doctor d order by d.specialty")
    List<String> distinctSpecialties();

    List<Doctor> findBySpecialtyOrderByFullNameAsc(String specialty);

    // Hata düzeltmesi: findByUsername() metodunun Optional<Doctor> döndürmesi sağlandı
    Optional<Doctor> findByUsername(String username);
}
