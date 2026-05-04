package com.internship.tool.controller;

import com.internship.tool.dto.ApiResponse;
import com.internship.tool.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    // ✅ UPLOAD FILE
    @PostMapping("/upload")
    public ResponseEntity<ApiResponse<String>> upload(@RequestParam("file") MultipartFile file) {

        String fileName = fileService.uploadFile(file);

        return ResponseEntity.ok(
                ApiResponse.<String>builder()
                        .success(true)
                        .message("File uploaded successfully")
                        .data(fileName)
                        .timestamp(java.time.LocalDateTime.now())
                        .build()
        );
    }

    // ✅ DOWNLOAD FILE
    @GetMapping("/{fileName}")
    public ResponseEntity<byte[]> getFile(@PathVariable String fileName) {

        byte[] file = fileService.getFile(fileName);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + fileName + "\"")
                .body(file);
    }
}