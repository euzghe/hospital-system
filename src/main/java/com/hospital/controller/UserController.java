package com.hospital.controller;

import com.hospital.model.User;
import com.hospital.repo.InMemoryDB;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/users")
public class UserController {

    @GetMapping("/doctors")
    public List<User> doctors(){
        return InMemoryDB.users.values().stream()
                .filter(u -> "doctor".equalsIgnoreCase(u.getRole()))
                .collect(Collectors.toList());
    }

    @GetMapping("/patients")
    public List<User> patients(){
        return InMemoryDB.users.values().stream()
                .filter(u -> "patient".equalsIgnoreCase(u.getRole()))
                .collect(Collectors.toList());
    }
}
