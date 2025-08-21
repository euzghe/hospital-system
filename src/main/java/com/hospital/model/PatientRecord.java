package com.hospital.model;

import jakarta.persistence.*;

@Entity
@Table(name = "patient_records")
public class PatientRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Hata düzeltmesi: TcNo alanı ve getter/setter'ları eklendi.
    @Column(name = "tc_no", length = 11)
    private String tcNo;

    @Column(name = "doctor_username", nullable = false, length = 80)
    private String doctorUsername;

    @Column(name = "patient_username", nullable = false, length = 80)
    private String patientUsername;

    // ISO: "yyyy-MM-dd'T'HH:mm"
    @Column(nullable = false, length = 16)
    private String datetime;

    @Column(length = 160)
    private String diagnosis;     // Tanı

    // *** EKLENDİ: Bulgular ***
    @Column(length = 500)
    private String findings;

    @Column(length = 300)
    private String labs;          // İstenen tahliller / sonuç özeti

    @Column(length = 500)
    private String notes;         // Serbest not

    @Column(name = "created_at", length = 16)
    private String createdAt;

    // --- getters & setters ---
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Hata düzeltmesi: TcNo için getter ve setter eklendi.
    public String getTcNo() {
        return tcNo;
    }

    public void setTcNo(String tcNo) {
        this.tcNo = tcNo;
    }

    public String getDoctorUsername() {
        return doctorUsername;
    }

    public void setDoctorUsername(String doctorUsername) {
        this.doctorUsername = doctorUsername;
    }

    public String getPatientUsername() {
        return patientUsername;
    }

    public void setPatientUsername(String patientUsername) {
        this.patientUsername = patientUsername;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getFindings() {
        return findings;
    }

    public void setFindings(String findings) {
        this.findings = findings;
    }

    public String getLabs() {
        return labs;
    }

    public void setLabs(String labs) {
        this.labs = labs;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}