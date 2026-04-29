package com.internship.tool.service;

import com.internship.tool.entity.ComplianceRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;


import java.time.LocalDateTime;
import java.util.List;

public interface ComplianceRecordService {

    ComplianceRecord create(ComplianceRecord record);

    ComplianceRecord getById(Long id);

    List<ComplianceRecord> getAll();

    ComplianceRecord update(Long id, ComplianceRecord record);

    void delete(Long id);

    Page<ComplianceRecord> getAllPaginated(PageRequest pageRequest);

    List<ComplianceRecord> search(String keyword);

    List<ComplianceRecord> filterByStatus(String status);

    List<ComplianceRecord> filterByDateRange(LocalDateTime start, LocalDateTime end);
}