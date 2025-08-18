package com.hospital.model;

public class Annotation {
    private Long id;
    private Long recordingId;
    private String author;     // username
    private String text;
    private Integer offsetSec; // opsiyonel
    private String datetime;

    public Annotation() {}
    public Annotation(Long id, Long recordingId, String author, String text, Integer offsetSec, String datetime) {
        this.id = id; this.recordingId = recordingId; this.author = author; this.text = text; this.offsetSec = offsetSec; this.datetime = datetime;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getRecordingId() { return recordingId; }
    public void setRecordingId(Long recordingId) { this.recordingId = recordingId; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
    public Integer getOffsetSec() { return offsetSec; }
    public void setOffsetSec(Integer offsetSec) { this.offsetSec = offsetSec; }
    public String getDatetime() { return datetime; }
    public void setDatetime(String datetime) { this.datetime = datetime; }
}
