package com.internship.tool.service;

import com.internship.tool.entity.Compliance;
import com.internship.tool.entity.AuditLog;
import com.internship.tool.repository.ComplianceRepository;
import com.internship.tool.repository.AuditLogRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ComplianceService {

    private final ComplianceRepository repository;
    private final AuditLogRepository auditRepo;

    public ComplianceService(ComplianceRepository repository,
                             AuditLogRepository auditRepo) {
        this.repository = repository;
        this.auditRepo = auditRepo;
    }

    public List<Compliance> getAll() {
        return repository.findAll();
    }

    public Compliance save(Compliance compliance) {

        Compliance saved = repository.save(compliance);

        AuditLog log = new AuditLog();
        log.setEntityType("Compliance");
        log.setEntityId(saved.getId());
        log.setAction("CREATE");
        log.setChangedBy("admin");
        log.setNewValue(saved.toString());

        auditRepo.save(log);

        return saved;
    }

    public void delete(Long id) {
    Compliance existing = repository.findById(id).orElse(null);

    repository.deleteById(id);

    AuditLog log = new AuditLog();
    log.setEntityType("Compliance");
    log.setEntityId(id);
    log.setAction("DELETE");
    log.setChangedBy("admin");

    if (existing != null) {
        log.setOldValue(existing.toString()); 
    }

    auditRepo.save(log);
}

    public List<Compliance> getByStatus(String status) {
        return repository.findByStatus(status);
    }

    public List<Compliance> getByDateRange(LocalDateTime start, LocalDateTime end) {
        return repository.findByDateRange(start, end);
    }
}