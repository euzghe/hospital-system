package com.hospital.model;

import java.util.Map;

public class Preset {
    private Long id;
    private String name;
    // roomId -> sourceId
    private Map<Long, Long> mapping;
    private String datetime;

    public Preset() {}
    public Preset(Long id, String name, Map<Long, Long> mapping, String datetime) {
        this.id = id; this.name = name; this.mapping = mapping; this.datetime = datetime;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Map<Long, Long> getMapping() { return mapping; }
    public void setMapping(Map<Long, Long> mapping) { this.mapping = mapping; }
    public String getDatetime() { return datetime; }
    public void setDatetime(String datetime) { this.datetime = datetime; }
}
