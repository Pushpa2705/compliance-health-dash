package com.internship.tool.repository;

import com.internship.tool.entity.Compliance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ComplianceRepository extends JpaRepository<Compliance, Long> {

   
    List<Compliance> findByStatus(String status);


    @Query("SELECT c FROM Compliance c WHERE c.createdAt BETWEEN :start AND :end")
    List<Compliance> findByDateRange(@Param("start") LocalDateTime start,
                                     @Param("end") LocalDateTime end);

    List<Compliance> findByTitleContainingIgnoreCaseAndDeletedFalse(String q);
@Query("SELECT COUNT(c) FROM Compliance c WHERE c.deleted = false")
long countActive();

@Query("SELECT COUNT(c) FROM Compliance c WHERE c.status = 'OPEN' AND c.deleted = false")
long countOpen();

@Query("SELECT COUNT(c) FROM Compliance c WHERE c.status = 'CLOSED' AND c.deleted = false")
long countClosed();

}