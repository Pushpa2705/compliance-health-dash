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
public class ComplianceRecordServiceImpl implements ComplianceRecordService {

    private final ComplianceRecordRepository repository;

    // ✅ CREATE
    @Override
    public ComplianceRecord create(ComplianceRecord record) {
        validate(record);
        return repository.save(record);
    }

    // ✅ GET BY ID
    @Override
    public ComplianceRecord getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Record not found with id: " + id));
    }

    // ✅ GET ALL
    @Override
    public List<ComplianceRecord> getAll() {
        return repository.findAll();
    }
    @Override
    public Page<ComplianceRecord> getAllPaginated(PageRequest pageRequest) {
        return repository.findAll(pageRequest);
    }

    // ✅ UPDATE
    @Override
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
    @Override
    public void delete(Long id) {
        ComplianceRecord existing = getById(id);
        repository.delete(existing);
    }

    // ✅ SEARCH
    @Override
    public List<ComplianceRecord> search(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            throw new ValidationException("Search keyword cannot be empty");
        }
        return repository.search(keyword);
    }

    // ✅ FILTER BY STATUS
    @Override
    public List<ComplianceRecord> filterByStatus(String status) {
        if (status == null || status.trim().isEmpty()) {
            throw new ValidationException("Status cannot be empty");
        }
        return repository.findByStatus(status);
    }

    // ✅ FILTER BY DATE RANGE
    @Override
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