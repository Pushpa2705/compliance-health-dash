package com.internship.tool.repository;

import com.internship.tool.entity.ComplianceRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ComplianceRecordRepository extends JpaRepository<ComplianceRecord, Long> {

    @Query("SELECT c FROM ComplianceRecord c WHERE LOWER(c.title) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
       "OR LOWER(c.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<ComplianceRecord> search(@Param("keyword") String keyword);
    List<ComplianceRecord> findByStatus(@Param("status") String status);

    List<ComplianceRecord> findByCreatedAtBetween(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}