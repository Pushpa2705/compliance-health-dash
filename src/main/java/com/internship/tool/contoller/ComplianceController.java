package com.internship.tool.controller;

import com.internship.tool.entity.Compliance;
import com.internship.tool.service.ComplianceService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import com.internship.tool.entity.AuditLog;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/compliance")
public class ComplianceController {

    private final ComplianceService service;

    public ComplianceController(ComplianceService service) {
        this.service = service;
    }

    @GetMapping
    public List<Compliance> getAll() {
        return service.getAll();
    }

    @PostMapping
    public Compliance create(@RequestBody Compliance compliance) {
        return service.save(compliance);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping("/status/{status}")
    public List<Compliance> getByStatus(@PathVariable String status) {
        return service.getByStatus(status);
    }

    @GetMapping("/date")
    public List<Compliance> getByDateRange(
            @RequestParam LocalDateTime start,
            @RequestParam LocalDateTime end
    ) {
        return service.getByDateRange(start, end);
    }
}