package com.wigglypaint.backend.controller;

import com.wigglypaint.backend.model.Drawing;
import com.wigglypaint.backend.repository.DrawingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/drawings")
@CrossOrigin(origins = "*")
public class DrawingController {

    @Autowired
    private DrawingRepository repository;

    // API 1: List all drawings (Metadata only)
    @GetMapping
    public List<Map<String, Object>> listDrawings() {
        return repository.findAll().stream()
                .map(d -> Map.of(
                    "id", (Object)d.getId(),
                    "name", (Object)d.getName(),
                    "createdAt", (Object)d.getCreatedAt()
                ))
                .collect(Collectors.toList());
    }

    // API 2: Save a drawing
    @PostMapping
    public Drawing saveDrawing(@RequestBody Drawing drawing) {
        return repository.save(drawing);
    }

    // API 3: Get a specific drawing
    @GetMapping("/{id}")
    public ResponseEntity<Drawing> getDrawing(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
