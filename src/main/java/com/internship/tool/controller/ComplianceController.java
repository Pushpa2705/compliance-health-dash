package com.internship.tool.controller;

import com.internship.tool.entity.Compliance;
import com.internship.tool.service.ComplianceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/compliance")
@CrossOrigin(origins = "*")
public class ComplianceController {

    private final ComplianceService service;

    public ComplianceController(ComplianceService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Compliance>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @PostMapping
    public ResponseEntity<Compliance> create(@RequestBody Compliance compliance) {
        return ResponseEntity.ok(service.save(compliance));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
