package com.internship.tool.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

@Service
public class FileService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    // ✅ UPLOAD FILE
    public String uploadFile(MultipartFile file) {

        System.out.println("File name: " + file.getOriginalFilename());
        System.out.println("Content type: " + file.getContentType());
        System.out.println("Size: " + file.getSize());

        // 🔥 Validate empty
        if (file.isEmpty()) {
            throw new RuntimeException("File is empty");
        }

        // 🔥 Validate size (<10MB)
        if (file.getSize() > 10 * 1024 * 1024) {
            throw new RuntimeException("File size exceeds 10MB");
        }

        // 🔥 Validate type (example: only images/pdf)
        String contentType = file.getContentType();
        if (contentType == null ||
                !(contentType.equals("image/png") ||
                  contentType.equals("image/jpeg") ||
                  contentType.equals("application/pdf"))) {
            throw new RuntimeException("Invalid file type");
        }

        try {
            // Create directory if not exists
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Generate UUID filename
            String originalName = file.getOriginalFilename();
            String extension = originalName.substring(originalName.lastIndexOf("."));
            String fileName = UUID.randomUUID() + extension;

            Path filePath = uploadPath.resolve(fileName);

            // Save file
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            return fileName;

        } catch (IOException e) {
            throw new RuntimeException("File upload failed");
        }
    }

    // ✅ GET FILE
    public byte[] getFile(String fileName) {
        try {
            Path filePath = Paths.get(uploadDir).resolve(fileName);
            return Files.readAllBytes(filePath);
        } catch (IOException e) {
            e.printStackTrace();   // 🔥 VERY IMPORTANT
            throw new RuntimeException("File upload failed: " + e.getMessage());
        }
        
    }
}