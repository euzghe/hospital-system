package com.hospital.model;

import jakarta.persistence.*;

@Entity
@Table(name = "patient_records", indexes = {
        @Index(name="idx_record_tc", columnList = "tcNo"),
        @Index(name="idx_record_patient", columnList = "patientUsername")
})
public class PatientRecord {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length=11, nullable=false)
    private String tcNo;

    @Column(length=64)
    private String patientUsername; // kolay filtre için

    @Column(length=25)
    private String datetime; // "yyyy-MM-dd HH:mm"

    @Column(length=150)
    private String diagnosis;

    @Column(length=500)
    private String notes;

    @Column(length=500)
    private String labs;

    public PatientRecord(){}

    // getters/setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTcNo() { return tcNo; }
    public void setTcNo(String tcNo) { this.tcNo = tcNo; }
    public String getPatientUsername() { return patientUsername; }
    public void setPatientUsername(String patientUsername) { this.patientUsername = patientUsername; }
    public String getDatetime() { return datetime; }
    public void setDatetime(String datetime) { this.datetime = datetime; }
    public String getDiagnosis() { return diagnosis; }
    public void setDiagnosis(String diagnosis) { this.diagnosis = diagnosis; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    public String getLabs() { return labs; }
    public void setLabs(String labs) { this.labs = labs; }
}
