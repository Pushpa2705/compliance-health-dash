package com.internship.tool.repository;

import com.internship.tool.entity.Compliance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComplianceRepository extends JpaRepository<Compliance, Long> {
}