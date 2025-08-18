package com.hospital.controller;

import com.hospital.model.Preset;
import com.hospital.repo.InMemoryDB;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/presets")
public class PresetController {

    @GetMapping
    public List<Preset> list() { return InMemoryDB.listPresets(); }

    @PostMapping
    public Preset create(@RequestBody Preset p, @RequestParam(defaultValue = "admin") String actor) {
        return InMemoryDB.addPreset(p, actor);
    }

    @PostMapping("/{id}/apply")
    public void apply(@PathVariable Long id, @RequestParam(defaultValue = "admin") String actor) {
        InMemoryDB.applyPreset(id, actor);
    }
}
