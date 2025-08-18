package com.hospital.controller;

import com.hospital.model.AuditEntry;
import com.hospital.model.Source;
import com.hospital.repo.InMemoryDB;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin(origins = "*")
public class AdminOpsController {

    @GetMapping("/audit")
    public List<AuditEntry> audit() {
        List<AuditEntry> out = new ArrayList<>(InMemoryDB.audit.values());
        out.sort(Comparator.comparing(AuditEntry::getDatetime));
        return out;
    }

    @PostMapping("/sources/{id}/status")
    public Source setStatus(@PathVariable Long id, @RequestBody Map<String, Object> body) {
        Source s = InMemoryDB.sources.get(id);
        if (s == null) throw new RuntimeException("Source not found");
        boolean online = Boolean.TRUE.equals(body.get("online"));
        s.setOnline(online);
        InMemoryDB.addAudit("admin","SOURCE_STATUS","id="+id+", online="+online);
        return s;
    }
}

