package com.hospital.repo;

import com.hospital.model.Clinic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClinicRepository extends JpaRepository<Clinic, Long> {
    boolean existsByName(String name);
    Optional<Clinic> findByNameIgnoreCase(String name);
}
