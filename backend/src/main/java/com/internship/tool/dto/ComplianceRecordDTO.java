package com.internship.tool.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComplianceRecordDTO {

    private Long id;
    private String title;
    private String description;
    private String status;
    private Integer score;
    private LocalDateTime dueDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}