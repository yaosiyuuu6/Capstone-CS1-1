package com.example.Neighborhood_Walk.controller;

import com.example.Neighborhood_Walk.entity.UserVerification;
import com.example.Neighborhood_Walk.Mapper.UserVerificationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/verifications")
public class UserVerificationController {

    @Autowired
    private UserVerificationMapper userVerificationMapper;

    // 创建新的 UserVerification
    @PostMapping("/create")
    public String createUserVerification(@RequestBody UserVerification userVerification) {
        userVerification.setVerificationId(UUID.randomUUID().toString());
        userVerificationMapper.insert(userVerification);
        return "UserVerification created successfully!";
    }

    // 获取特定 UserVerification
    @GetMapping("/{id}")
    public UserVerification getUserVerificationById(@PathVariable("id") String verificationId) {
        return userVerificationMapper.selectById(verificationId);
    }

    // 获取所有 UserVerifications
    @GetMapping("/all")
    public List<UserVerification> getAllUserVerifications() {
        return userVerificationMapper.selectList(null);
    }

    // 更新 UserVerification
    @PutMapping("/{id}")
    public String updateUserVerification(@PathVariable("id") String verificationId, @RequestBody UserVerification userVerification) {
        UserVerification existingVerification = userVerificationMapper.selectById(verificationId);
        if (existingVerification == null) {
            return "UserVerification not found.";
        }
        userVerification.setVerificationId(verificationId);
        userVerificationMapper.updateById(userVerification);
        return "UserVerification updated successfully!";
    }

    // 删除 UserVerification（需要管理员权限）
    @DeleteMapping("/{id}")
    public String deleteUserVerification(@PathVariable("id") String verificationId, @RequestHeader("userType") String userType) {
        if (!"Admin".equalsIgnoreCase(userType)) {
            return "You do not have permission to delete UserVerifications.";
        }

        int result = userVerificationMapper.deleteById(verificationId);
        if (result > 0) {
            return "UserVerification deleted successfully!";
        } else {
            return "UserVerification not found.";
        }
    }
}
