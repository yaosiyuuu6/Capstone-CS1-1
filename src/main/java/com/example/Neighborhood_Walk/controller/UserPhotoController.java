//package com.example.Neighborhood_Walk.controller;
//
//import com.example.Neighborhood_Walk.Mapper.UserPhotoMapper;
//import com.example.Neighborhood_Walk.entity.UserPhoto;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.util.List;
//import java.util.UUID;
//
//@RestController
//@RequestMapping("/photos")
//public class UserPhotoController {
//
//    @Autowired
//    private S3Service s3Service; // S3 上传服务
//
//    @Autowired
//    private UserPhotoMapper userPhotoMapper; // 数据库操作
//
//    // 上传用户照片
//    @PostMapping("/upload")
//    public ResponseEntity<String> uploadPhoto(@RequestParam String userId, @RequestParam("file") MultipartFile file) {
//        try {
//            // 将文件上传到S3
//            String fileUrl = s3Service.uploadFile(userId + "/" + file.getOriginalFilename(), file);
//
//            // 将上传后的文件URL保存到数据库
//            UserPhoto userPhoto = new UserPhoto();
//            userPhoto.setPhotoId(UUID.randomUUID().toString());
//            userPhoto.setUserId(userId);
//            userPhoto.setPhotoUrl(fileUrl);
//            userPhotoMapper.insertUserPhoto(userPhoto);
//
//            return ResponseEntity.ok("Photo uploaded successfully with URL: " + fileUrl);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Photo upload failed.");
//        }
//    }
//
//    // 获取用户的照片
//    @GetMapping("/{userId}")
//    public ResponseEntity<List<UserPhoto>> getUserPhotos(@PathVariable String userId) {
//        List<UserPhoto> photos = userPhotoMapper.findPhotosByUserId(userId);
//        return ResponseEntity.ok(photos);
//    }
//}
