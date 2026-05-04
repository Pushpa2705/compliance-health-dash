package com.internship.tool.service;

import com.internship.tool.entity.ComplianceRecord;
import com.internship.tool.exception.ResourceNotFoundException;
import com.internship.tool.exception.ValidationException;
import com.internship.tool.repository.ComplianceRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ComplianceRecordServiceImpl {

    private final ComplianceRecordRepository repository;

    // ✅ CREATE
    public ComplianceRecord create(ComplianceRecord record) {
        validate(record);
        return repository.save(record);
    }

    // ✅ GET BY ID
   
    public ComplianceRecord getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Record not found with id: " + id));
    }

    // ✅ GET ALL
    
    public List<ComplianceRecord> getAll() {
        return repository.findAll();
    }
    
    public Page<ComplianceRecord> getAllPaginated(PageRequest pageRequest) {
        return repository.findAll(pageRequest);
    }

    // ✅ UPDATE
   
    public ComplianceRecord update(Long id, ComplianceRecord record) {
        ComplianceRecord existing = getById(id);

        validate(record);

        existing.setTitle(record.getTitle());
        existing.setDescription(record.getDescription());
        existing.setStatus(record.getStatus());
        existing.setScore(record.getScore());
        existing.setDueDate(record.getDueDate());

        return repository.save(existing);
    }

    // ✅ DELETE
   
    public void delete(Long id) {
        ComplianceRecord existing = getById(id);
        repository.delete(existing);
    }

    // ✅ SEARCH
 
    public List<ComplianceRecord> search(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            throw new ValidationException("Search keyword cannot be empty");
        }
        return repository.search(keyword);
    }

    // ✅ FILTER BY STATUS
  
    public List<ComplianceRecord> filterByStatus(String status) {
        if (status == null || status.trim().isEmpty()) {
            throw new ValidationException("Status cannot be empty");
        }
        return repository.findByStatus(status);
    }

    // ✅ FILTER BY DATE RANGE

    public List<ComplianceRecord> filterByDateRange(LocalDateTime start, LocalDateTime end) {
        if (start == null || end == null) {
            throw new ValidationException("Date range cannot be null");
        }
        return repository.findByCreatedAtBetween(start, end);
    }

    // 🔒 COMMON VALIDATION
    private void validate(ComplianceRecord record) {
        if (record.getTitle() == null || record.getTitle().trim().isEmpty()) {
            throw new ValidationException("Title is required");
        }

        if (record.getScore() != null &&
                (record.getScore() < 0 || record.getScore() > 100)) {
            throw new ValidationException("Score must be between 0 and 100");
        }
    }
}