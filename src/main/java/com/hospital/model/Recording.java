package com.hospital.model;

public class Recording {
    private Long id;
    private Long roomId;
    private Long sourceId;
    private String doctor;   // username
    private String patient;  // username
    private String title;
    private String url;      // tipik olarak source.url
    private String datetime;

    public Recording() {}

    public Recording(Long id, Long roomId, Long sourceId, String doctor, String patient,
                     String title, String url, String datetime) {
        this.id = id; this.roomId = roomId; this.sourceId = sourceId;
        this.doctor = doctor; this.patient = patient;
        this.title = title; this.url = url; this.datetime = datetime;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getRoomId() { return roomId; }
    public void setRoomId(Long roomId) { this.roomId = roomId; }
    public Long getSourceId() { return sourceId; }
    public void setSourceId(Long sourceId) { this.sourceId = sourceId; }
    public String getDoctor() { return doctor; }
    public void setDoctor(String doctor) { this.doctor = doctor; }
    public String getPatient() { return patient; }
    public void setPatient(String patient) { this.patient = patient; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
    public String getDatetime() { return datetime; }
    public void setDatetime(String datetime) { this.datetime = datetime; }
}
