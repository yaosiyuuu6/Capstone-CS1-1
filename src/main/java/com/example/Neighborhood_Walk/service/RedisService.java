package com.example.Neighborhood_Walk.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    // 将验证码存储到 Redis 中，有效期为 5 分钟
    // Store the verification code in Redis with a validity of 5 minutes
    public void storeVerificationCode(String key, String verificationCode) {
        redisTemplate.opsForValue().set(key, verificationCode, 5, TimeUnit.MINUTES); // 设置过期时间为5分钟
        // Set expiration time to 5 minutes
    }

    // 从 Redis 获取验证码
    // Get the verification code from Redis
    public String getVerificationCode(String key) {
        return (String) redisTemplate.opsForValue().get(key); // 获取验证码
        // Get the verification code
    }

    // 标记某个联系方式的验证状态为已验证
    // Mark the verification status of the contact method as verified
    public void markAsVerified(String key) {
        redisTemplate.opsForValue().set(key + ":verified", true, 30, TimeUnit.MINUTES); // 设置为已验证，缓存30分钟
        // Set as verified, cache for 30 minutes
    }

    // 检查是否已经验证
    // Check if the contact method has been verified
    public Boolean isVerified(String key) {
        return (Boolean) redisTemplate.opsForValue().get(key + ":verified"); // 检查验证状态
        // Check verification status
    }

    // 清除验证码和验证状态
    // Clear verification code and status
    public void clearVerificationData(String key) {
        redisTemplate.delete(key); // 删除验证码
        // Delete the verification code
        redisTemplate.delete(key + ":verified"); // 删除验证状态
        // Delete the verification status
    }
}

