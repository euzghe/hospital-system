package com.hospital.repo;

import com.hospital.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    // Spring Data JPA'nın otomatik olarak uygulayacağı sorgu metotları

    // Kullanıcı adıyla kullanıcı bulur
    Optional<User> findByUsername(String username);

    // Belirli bir role sahip tüm kullanıcıları bulur
    List<User> findByRole(String role);

    // Belirli bir role ve kliniğe sahip kullanıcıları soyadına göre sıralayarak bulur
    List<User> findByRoleAndClinicIgnoreCaseOrderByLastNameAsc(String role, String clinic);
}