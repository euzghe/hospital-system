package com.hospital.model;

public class Route {
    private Long roomId;
    private Long sourceId;
    private String datetime; // basit string

    public Route() {}
    public Route(Long roomId, Long sourceId, String datetime) {
        this.roomId = roomId; this.sourceId = sourceId; this.datetime = datetime;
    }

    public Long getRoomId() { return roomId; }
    public void setRoomId(Long roomId) { this.roomId = roomId; }
    public Long getSourceId() { return sourceId; }
    public void setSourceId(Long sourceId) { this.sourceId = sourceId; }
    public String getDatetime() { return datetime; }
    public void setDatetime(String datetime) { this.datetime = datetime; }
}
