package com.hospital.model;

import jakarta.persistence.*;

@Entity
@Table(name = "clinics")
public class Clinic {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 120)
    private String name;

    public Clinic() {}
    public Clinic(String name){ this.name = name; }

    public Long getId() { return id; }
    public String getName() { return name; }
    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
}
