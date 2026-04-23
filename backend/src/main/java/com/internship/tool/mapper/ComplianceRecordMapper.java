package com.internship.tool.mapper;

import com.internship.tool.dto.ComplianceRecordDTO;
import com.internship.tool.entity.ComplianceRecord;

public class ComplianceRecordMapper {

    public static ComplianceRecordDTO toDTO(ComplianceRecord entity) {
        return ComplianceRecordDTO.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .status(entity.getStatus())
                .score(entity.getScore())
                .dueDate(entity.getDueDate())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public static ComplianceRecord toEntity(ComplianceRecordDTO dto) {
        return ComplianceRecord.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .description(dto.getDescription())
                .status(dto.getStatus())
                .score(dto.getScore())
                .dueDate(dto.getDueDate())
                .build();
    }
}