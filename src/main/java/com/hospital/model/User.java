package com.hospital.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = "username"))
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, length=64)
    private String username;

    @Column(nullable=false, length=128)
    private String password;

    @Column(nullable=false, length=16)
    private String role; // admin | doctor | patient

    // Eklenen alanlar:
    @Column(length=64)
    private String firstName;

    @Column(length=64)
    private String lastName;

    @Column(length=64)
    private String clinic; // doktor ise, hangi klinikte çalıştığı bilgisi

    @Column(length=120)
    private String fullName; // Aslında `firstName` ve `lastName` ile birleştirilebilirdi.

    @Column(length=11)
    private String tcNo; // hasta ise

    public User() {}
    public User(String username, String password, String role) {
        this.username=username; this.password=password; this.role=role;
    }

    // getters/setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    // Yeni eklenen metotlar
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getClinic() { return clinic; }
    public void setClinic(String clinic) { this.clinic = clinic; }

    // Mevcut metotlar
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getTcNo() { return tcNo; }
    public void setTcNo(String tcNo) { this.tcNo = tcNo; }
}