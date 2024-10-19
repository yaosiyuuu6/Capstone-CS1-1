package com.example.Neighborhood_Walk.controller;

import com.example.Neighborhood_Walk.service.MinioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class FileUploadController {

    @Autowired
    private MinioService minioService;

    @PostMapping("/upload-avatar")
    public Map<String, String> uploadAvatar(@RequestParam("avatar") MultipartFile file) {
        Map<String, String> response = new HashMap<>();
        try {
            // 上传文件并返回 MinIO 中的 URL
            String fileUrl = minioService.uploadFile(file);
            response.put("fileUrl", fileUrl);
        } catch (Exception e) {
            response.put("error", "Failed to upload file: " + e.getMessage());
        }
        return response;
    }
}
