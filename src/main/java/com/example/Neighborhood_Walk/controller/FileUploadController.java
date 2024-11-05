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
            // Upload the file to MinIO and get the file URL
            String fileUrl = minioService.uploadFile(file, userId);

            // Log the file URL to the console for debugging
            System.out.println(fileUrl);

            // Create a new UserPhoto object and set its properties
            UserPhoto userPhoto = new UserPhoto();
            userPhoto.setPhotoId(UUID.randomUUID().toString()); // Generate a new UUID for photoId
            userPhoto.setUserId(userId); // Set the user ID
            userPhoto.setPhotoUrl(fileUrl); // Set the URL of the uploaded photo

            // Delete the existing photo for the user (if any) before inserting the new one
            userPhotoMapper.deleteByUserId(userId);

            // Insert the new photo record into the database
            userPhotoMapper.insertUserPhoto(userPhoto);

            // Add the file URL to the response map to return to the client
            response.put("fileUrl", fileUrl);
        } catch (Exception e) {
            // Handle any exceptions and return an error message in the response
            response.put("error", "Failed to upload file: " + e.getMessage());
        }

        return response;
    }

}
