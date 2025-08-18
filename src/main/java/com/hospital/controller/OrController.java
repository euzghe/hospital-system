package com.hospital.controller;

import com.hospital.model.*;
import com.hospital.repo.InMemoryDB;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/or")
public class OrController {

    @GetMapping("/rooms")
    public List<Room> rooms() { return InMemoryDB.allRooms(); }

    @GetMapping("/sources")
    public List<Source> sources() { return InMemoryDB.allSources(); }

    @GetMapping("/routes")
    public List<Route> routes() { return InMemoryDB.allRoutes(); }

    @GetMapping("/route/{roomId}")
    public Map<String, Object> route(@PathVariable Long roomId) {
        Route r = InMemoryDB.getRoute(roomId);
        Map<String, Object> res = new HashMap<>();
        res.put("route", r);
        res.put("source", r != null ? InMemoryDB.findSource(r.getSourceId()) : null);
        return res;
    }

    @PostMapping("/route")
    public Map<String, Object> setRoute(@RequestBody Map<String, Object> body) {
        Long roomId = ((Number) body.get("roomId")).longValue();
        Long sourceId = ((Number) body.get("sourceId")).longValue();
        String actor = (String) body.getOrDefault("actor", "system");

        Source s = InMemoryDB.findSource(sourceId);
        if (s == null || !s.isOnline()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Source offline veya bulunamadı");
        }
        Route r = InMemoryDB.setRoute(roomId, sourceId, actor);
        Map<String, Object> res = new HashMap<>();
        res.put("route", r);
        res.put("source", s);
        return res;
    }
}
