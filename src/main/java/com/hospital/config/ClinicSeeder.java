package com.hospital.config;

import com.hospital.model.Clinic;
import com.hospital.model.User;
import com.hospital.repo.ClinicRepository;
import com.hospital.repo.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.*;

@Configuration
public class ClinicSeeder {

    @Bean
    CommandLineRunner seedClinics(ClinicRepository clinicRepo, UserRepository userRepo) {
        return args -> {
            String[] names = {
                    "Kardiyoloji","KBB","Göz Hastalıkları","Dahiliye",
                    "Nöroloji","Beyin ve Sinir Cerrahisi","Genel Cerrahi",
                    "Ortopedi","Üroloji","Göğüs Hastalıkları","Dermatoloji",
                    "Endokrinoloji","Gastroenteroloji","Hematoloji","Onkoloji",
                    "Romatoloji","Enfeksiyon","Fizik Tedavi","Psikiyatri","Pediatri"
            };

            for (String n : names) {
                if (!clinicRepo.existsByName(n)) {
                    clinicRepo.save(new Clinic(n));
                }
            }

            // Doktorların clinic alanı boşsa rastgele ata
            List<Clinic> all = clinicRepo.findAll();
            if (all.isEmpty()) return;
            Random r = new Random();

            List<User> doctors = userRepo.findByRole("doctor");
            boolean anyChange = false;
            for (User d : doctors) {
                if (d.getClinic() == null || d.getClinic().isBlank()) {
                    Clinic c = all.get(r.nextInt(all.size()));
                    d.setClinic(c.getName());
                    anyChange = true;
                }
            }
            if (anyChange) userRepo.saveAll(doctors);
        };
    }
}
