package com.example.Neighborhood_Walk.controller;

import com.example.Neighborhood_Walk.Mapper.UserPhotoMapper;
import com.example.Neighborhood_Walk.entity.UserPhoto;
import com.example.Neighborhood_Walk.service.MinioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class FileUploadController {

    @Autowired
    private MinioService minioService;

    @Autowired
    private UserPhotoMapper userPhotoMapper; // 数据库操作

    @PostMapping("/upload-avatar")
    public Map<String, String> uploadAvatar(@RequestParam String userId, @RequestParam("avatar") MultipartFile file) {
        Map<String, String> response = new HashMap<>();
        try {
            // 上传文件并返回 MinIO 中的 URL
            String fileUrl = minioService.uploadFile(file);

            System.out.println(fileUrl);
            // 将上传后的文件URL保存到数据库
            UserPhoto userPhoto = new UserPhoto();
            userPhoto.setPhotoId(UUID.randomUUID().toString());
            userPhoto.setUserId(userId);
            userPhoto.setPhotoUrl(fileUrl);
            userPhotoMapper.deleteByUserId(userId);
            userPhotoMapper.insertUserPhoto(userPhoto);

            response.put("fileUrl", fileUrl);
        } catch (Exception e) {
            response.put("error", "Failed to upload file: " + e.getMessage());
        }
        return response;
    }
}
