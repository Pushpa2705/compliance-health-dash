package com.internship.tool.controller;

import com.internship.tool.entity.Compliance;
import com.internship.tool.service.ComplianceService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import com.internship.tool.entity.AuditLog;
import java.util.Map;
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
        service.softDelete(id);
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
    @PutMapping("/{id}")
    public ResponseEntity<Compliance> update(@PathVariable Long id,
           @RequestBody Compliance compliance) 
    {
    return ResponseEntity.ok(service.update(id, compliance));
    }
    @GetMapping("/search")
    public ResponseEntity<List<Compliance>> search(@RequestParam String q) 
    {
    return ResponseEntity.ok(service.search(q));
}

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Long>> stats()
    {
    return ResponseEntity.ok(service.getStats());
    }
}