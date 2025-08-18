package com.hospital.model;

import jakarta.persistence.*;

@Entity
@Table(name = "appointments", indexes = {
        @Index(name="idx_appt_doctor", columnList = "doctorUsername"),
        @Index(name="idx_appt_patient", columnList = "patientUsername")
})
public class Appointment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, length=64)
    private String doctorUsername;

    @Column(nullable=false, length=64)
    private String patientUsername;

    @Column(nullable=false, length=25)
    private String datetime; // ISO string

    @Column(length=200)
    private String reason;

    @Column(length=20)
    private String status; // scheduled | cancelled | done

    @Column(length=25)
    private String createdAt;

    public Appointment() {}

    // getters/setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getDoctorUsername() { return doctorUsername; }
    public void setDoctorUsername(String doctorUsername) { this.doctorUsername = doctorUsername; }
    public String getPatientUsername() { return patientUsername; }
    public void setPatientUsername(String patientUsername) { this.patientUsername = patientUsername; }
    public String getDatetime() { return datetime; }
    public void setDatetime(String datetime) { this.datetime = datetime; }
    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
}
