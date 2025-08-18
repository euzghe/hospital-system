package com.hospital.model;

public class AuditEntry {
    private Long id;
    private String actor;   // username veya "system"
    private String action;  // "ROUTE_SET", "PRESET_APPLIED", "RECORDING_CREATED"...
    private String details; // kısa açıklama
    private String datetime;

    public AuditEntry() {}
    public AuditEntry(Long id, String actor, String action, String details, String datetime) {
        this.id = id; this.actor = actor; this.action = action; this.details = details; this.datetime = datetime;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getActor() { return actor; }
    public void setActor(String actor) { this.actor = actor; }
    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }
    public String getDetails() { return details; }
    public void setDetails(String details) { this.details = details; }
    public String getDatetime() { return datetime; }
    public void setDatetime(String datetime) { this.datetime = datetime; }
}
