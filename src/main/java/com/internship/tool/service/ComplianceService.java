package com.internship.tool.service;

import com.internship.tool.entity.Compliance;
import com.internship.tool.repository.ComplianceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComplianceService {

    private final ComplianceRepository repository;

    public ComplianceService(ComplianceRepository repository) {
        this.repository = repository;
    }

    public List<Compliance> getAll() {
        return repository.findAll();
    }

    public Compliance save(Compliance compliance) {
        return repository.save(compliance);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}