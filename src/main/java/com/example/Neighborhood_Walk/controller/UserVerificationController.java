package com.example.Neighborhood_Walk.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.Neighborhood_Walk.Mapper.UserMapper;
import com.example.Neighborhood_Walk.entity.User;
import com.example.Neighborhood_Walk.entity.UserVerification;
import com.example.Neighborhood_Walk.Mapper.UserVerificationMapper;
import com.example.Neighborhood_Walk.service.EmailService;
import com.example.Neighborhood_Walk.service.SmsService;
import com.example.Neighborhood_Walk.util.VerificationCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/verifications")
public class UserVerificationController {

    @Autowired
    private UserVerificationMapper userVerificationMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private EmailService emailService;

    @Autowired
    private SmsService smsService;

    // Create UserVerification
    @PostMapping("/create")
    public String createUserVerification(@RequestBody UserVerification userVerification) {
        userVerification.setVerificationId(UUID.randomUUID().toString());
        userVerificationMapper.insert(userVerification);
        return "UserVerification created successfully!";
    }

    // Get UserVerification
    @GetMapping("/{id}")
    public UserVerification getUserVerificationById(@PathVariable("id") String verificationId) {
        return userVerificationMapper.selectById(verificationId);
    }

    //Get all UserVerifications
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

    /**
     * 发送验证码到邮箱并保存到数据库
     */
    @PostMapping("/send-verification-code-email")
    public String sendVerificationCode(@RequestParam String email) {
        System.out.println("Received request to send verification code to email: " + email);

        // 查询用户是否存在
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("email", email));
        if (user == null) {
            System.out.println("No user found with email: " + email);
            return "Email not registered.";
        }
        System.out.println("User found: " + user.getUserId());

        // 查询是否已有 UserVerification 记录
        UserVerification userVerification = userVerificationMapper.selectOne(
                new QueryWrapper<UserVerification>()
                        .eq("user_id", user.getUserId())
                        .eq("verification_type", "email")
        );

        if (userVerification == null) {
            // 如果不存在，创建一个新的UserVerification记录
            System.out.println("No existing verification record found, creating a new one.");
            userVerification = new UserVerification();
            userVerification.setVerificationId(UUID.randomUUID().toString());
            userVerification.setUserId(user.getUserId());
            userVerification.setVerificationType("email");
            userVerification.setVerificationStatus("Unverified");
            userVerification.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
            userVerification.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        } else {
            System.out.println("Existing verification record found: " + userVerification.getVerificationId());
        }

        // 生成验证码并保存到 information 字段
        String verificationCode = VerificationCodeGenerator.generateVerificationCode();
        System.out.println("Generated verification code: " + verificationCode);
        userVerification.setInformation(verificationCode);
        userVerification.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));

        // 更新或插入 UserVerification 记录
        if (userVerification.getVerificationId() == null || userVerificationMapper.selectById(userVerification.getVerificationId()) == null) {
            System.out.println("Inserting new verification record for user: " + user.getUserId());
            userVerificationMapper.insert(userVerification);
            System.out.println("Verification record inserted.");
        } else {
            System.out.println("Updating existing verification record: " + userVerification.getVerificationId());
            userVerificationMapper.updateById(userVerification);
            System.out.println("Verification record updated.");
        }

        // 发送验证码到邮箱
        emailService.sendVerificationEmail(email, verificationCode);
        System.out.println("Verification code sent to email: " + email);

        return "Verification code sent successfully!";
    }


    @PostMapping("/verify-email")
    public String verifyEmail(@RequestParam String email, @RequestParam String code) {
        // 查询用户是否存在
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("email", email));
        if (user == null) {
            return "Email not registered.";
        }

        // 查询用户的验证记录
        UserVerification userVerification = userVerificationMapper.selectOne(
                new QueryWrapper<UserVerification>()
                        .eq("user_id", user.getUserId())
                        .eq("verification_type", "email")
        );

        if (userVerification == null) {
            return "No verification record found.";
        }

        // 验证验证码
        if (!userVerification.getInformation().equals(code)) {
            return "Invalid verification code.";
        }

        // 更新验证状态为已验证
        userVerification.setVerificationStatus("Verified");
        userVerification.setVerificationDate(Timestamp.valueOf(LocalDateTime.now()));
        userVerification.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));

        // 保存更新后的记录
        userVerificationMapper.updateById(userVerification);

        return "Email verified successfully!";
    }


    /**
     * Send verification code to phone and save to database
     */
    @PostMapping("/send-verification-code-phone")
    public String sendVerificationCodeToPhone(@RequestParam String phoneNumber) {
        System.out.println("Received request to send verification code to phone: " + phoneNumber);

        // Check if user exists with the phone number
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("phone_number", phoneNumber));
        if (user == null) {
            System.out.println("No user found with phone: " + phoneNumber);
            return "Phone number not registered.";
        }
        System.out.println("User found: " + user.getUserId());

        // Check if there is an existing UserVerification record
        UserVerification userVerification = userVerificationMapper.selectOne(
                new QueryWrapper<UserVerification>()
                        .eq("user_id", user.getUserId())
                        .eq("verification_type", "phone")
        );

        if (userVerification == null) {
            // If not, create a new UserVerification record
            System.out.println("No existing verification record found, creating a new one.");
            userVerification = new UserVerification();
            userVerification.setVerificationId(UUID.randomUUID().toString());
            userVerification.setUserId(user.getUserId());
            userVerification.setVerificationType("phone");
            userVerification.setVerificationStatus("Unverified");
            userVerification.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
            userVerification.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        } else {
            System.out.println("Existing verification record found: " + userVerification.getVerificationId());
        }

        // Generate verification code and save to information field
        String verificationCode = VerificationCodeGenerator.generateVerificationCode();
        System.out.println("Generated verification code: " + verificationCode);
        userVerification.setInformation(verificationCode);
        userVerification.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));

        // Insert or update UserVerification record
        if (userVerification.getVerificationId() == null || userVerificationMapper.selectById(userVerification.getVerificationId()) == null) {
            System.out.println("Inserting new verification record for user: " + user.getUserId());
            userVerificationMapper.insert(userVerification);
            System.out.println("Verification record inserted.");
        } else {
            System.out.println("Updating existing verification record: " + userVerification.getVerificationId());
            userVerificationMapper.updateById(userVerification);
            System.out.println("Verification record updated.");
        }

        // Send verification code via SMS
        smsService.sendSms(phoneNumber, "Your verification code is: " + verificationCode);
        System.out.println("Verification code sent to phone: " + phoneNumber);

        return "Verification code sent successfully!";
    }

    @PostMapping("/verify-phone")
    public String verifyPhone(@RequestParam String phoneNumber, @RequestParam String code) {
        // Check if user exists with the phone number
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("phone_number", phoneNumber));
        if (user == null) {
            return "Phone number not registered.";
        }

        // Check for existing verification record
        UserVerification userVerification = userVerificationMapper.selectOne(
                new QueryWrapper<UserVerification>()
                        .eq("user_id", user.getUserId())
                        .eq("verification_type", "phone")
        );

        if (userVerification == null) {
            return "No verification record found.";
        }

        // Validate the verification code
        if (!userVerification.getInformation().equals(code)) {
            return "Invalid verification code.";
        }

        // Update verification status to 'Verified'
        userVerification.setVerificationStatus("Verified");
        userVerification.setVerificationDate(Timestamp.valueOf(LocalDateTime.now()));
        userVerification.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));

        // Save the updated verification record
        userVerificationMapper.updateById(userVerification);

        return "Phone verified successfully!";
    }




}
