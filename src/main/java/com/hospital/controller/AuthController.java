package com.hospital.controller;

import com.hospital.model.User;
import com.hospital.repo.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@Controller
@CrossOrigin(origins = "*")
public class AuthController {

    private final UserRepository users;

    public AuthController(UserRepository users) {
        this.users = users;
    }

    // Ana sayfa yönlendirmesi
    @GetMapping("/")
    public String loginPage() {
        return "login.html";
    }

    // Doktor paneli için yönlendirme
    @GetMapping("/doctor")
    public String doctorPanel() {
        return "doctor.html";
    }

    // Hasta paneli için yönlendirme
    @GetMapping("/patient")
    public String patientPanel() {
        return "patient.html";
    }

    // API uç noktası için @ResponseBody kullanarak JSON döndürmeye devam ediyoruz
    @PostMapping("/login")
    @ResponseBody // Bu anotasyon, metodun dönüş değerinin doğrudan HTTP yanıt gövdesine yazılmasını sağlar
    public Map<String, String> login(@RequestBody Map<String, String> body) {
        String username = body.getOrDefault("username", "");
        String password = body.getOrDefault("password", "");

        Map<String, String> res = new HashMap<>();
        Optional<User> opt = users.findByUsername(username);
        if (opt.isPresent() && Objects.equals(opt.get().getPassword(), password)) {
            User u = opt.get();
            res.put("status","success");
            res.put("message","Giriş başarılı.");
            res.put("username", u.getUsername());
            res.put("role", u.getRole());
        } else {
            res.put("status","error");
            res.put("message","Geçersiz giriş.");
        }
        return res;
    }
}
