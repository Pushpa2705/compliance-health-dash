package com.internship.tool.controller;

import com.internship.tool.dto.ApiResponse;
import com.internship.tool.dto.ComplianceRecordDTO;
import com.internship.tool.entity.ComplianceRecord;
import com.internship.tool.mapper.ComplianceRecordMapper;
import com.internship.tool.service.ComplianceRecordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/compliance")
@RequiredArgsConstructor
public class ComplianceRecordController {

    private final ComplianceRecordService service;

    // ✅ CREATE (201)
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<ComplianceRecordDTO>> create(@Valid @RequestBody ComplianceRecordDTO dto) {

        ComplianceRecord saved = service.create(ComplianceRecordMapper.toEntity(dto));

        return new ResponseEntity<>(
                ApiResponse.<ComplianceRecordDTO>builder()
                        .success(true)
                        .message("Record created successfully")
                        .data(ComplianceRecordMapper.toDTO(saved))
                        .build(),
                HttpStatus.CREATED
        );
    }

    // ✅ GET ALL WITH PAGINATION
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<Page<ComplianceRecordDTO>>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        Page<ComplianceRecordDTO> result =
                service.getAllPaginated(PageRequest.of(page, size))
                        .map(ComplianceRecordMapper::toDTO);

        return ResponseEntity.ok(
                ApiResponse.<Page<ComplianceRecordDTO>>builder()
                        .success(true)
                        .message("Records fetched successfully")
                        .data(result)
                        .build()
        );
    }

    // ✅ GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ComplianceRecordDTO>> getById(@PathVariable Long id) {

        ComplianceRecord record = service.getById(id);

        return ResponseEntity.ok(
                ApiResponse.<ComplianceRecordDTO>builder()
                        .success(true)
                        .message("Record fetched successfully")
                        .data(ComplianceRecordMapper.toDTO(record))
                        .build()
        );
    }

    // ✅ UPDATE (fixed → use DTO instead of Entity)
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ComplianceRecordDTO>> update(
            @PathVariable Long id,
            @Valid @RequestBody ComplianceRecordDTO dto
    ) {
        ComplianceRecord updated = service.update(id, ComplianceRecordMapper.toEntity(dto));

        return ResponseEntity.ok(
                ApiResponse.<ComplianceRecordDTO>builder()
                        .success(true)
                        .message("Record updated successfully")
                        .data(ComplianceRecordMapper.toDTO(updated))
                        .build()
        );
    }

    // ✅ DELETE (204 improved)
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {

        service.delete(id);

        return new ResponseEntity<>(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("Record deleted successfully")
                        .data(null)
                        .build(),
                HttpStatus.NO_CONTENT
        );
    }
}