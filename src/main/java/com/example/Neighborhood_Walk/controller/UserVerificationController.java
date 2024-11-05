package com.example.Neighborhood_Walk.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.Neighborhood_Walk.Mapper.UserMapper;
import com.example.Neighborhood_Walk.entity.User;
import com.example.Neighborhood_Walk.entity.UserVerification;
import com.example.Neighborhood_Walk.Mapper.UserVerificationMapper;
import com.example.Neighborhood_Walk.service.EmailService;
import com.example.Neighborhood_Walk.service.RedisService;
import com.example.Neighborhood_Walk.service.SmsService;
import com.example.Neighborhood_Walk.util.VerificationCodeGenerator;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

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

    @Autowired
    private RedisService redisService;

    @Value("${digitalid.client_id}")
    private String clientId;

    @Value("${digitalid.client_secret}")
    private String clientSecret;

    @Value("${digitalid.auth_url}")
    private String authUrl;

    private static final Logger logger = LoggerFactory.getLogger(UserVerificationController.class);


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

    @PostMapping("/send-verification-code-email")
    public String sendVerificationCode(@RequestParam String email) {
        // 查询是否有用户已注册该邮箱并通过验证
        // Check if any user has already registered and verified this email
        User existingUser = userMapper.selectOne(new QueryWrapper<User>().eq("email", email));

        if (existingUser != null) {
            // 查询该用户的邮箱验证状态
            // Check if the user's email verification status
            UserVerification emailVerification = userVerificationMapper.selectOne(
                    new QueryWrapper<UserVerification>()
                            .eq("user_id", existingUser.getUserId())
                            .eq("verification_type", "email")
            );

            // 如果邮箱已被验证，拒绝发送验证码
            // If the email is already verified, reject sending verification code
            if (emailVerification != null && "Verified".equals(emailVerification.getVerificationStatus())) {
                return "Email is already registered and verified."; // 邮箱已被注册且验证
            }
        }
        // 生成验证码
        // Generate a verification code
        String verificationCode = VerificationCodeGenerator.generateVerificationCode();

        // 将验证码保存到 Redis，有效期为5分钟
        // Store the verification code in Redis with a 5-minute expiration
        redisService.storeVerificationCode(email, verificationCode);

        // 发送验证码到邮箱
        // Send the verification code to the email
        emailService.sendVerificationEmail(email, verificationCode);

        return "Verification code sent successfully!";
    }

    @PostMapping("/verify-email")
    public String verifyEmail(@RequestParam String email, @RequestParam String code) {
        // 从 Redis 获取验证码
        // Get the verification code from Redis
        String cachedCode = redisService.getVerificationCode(email);

        // 验证码是否过期或无效
        // Check if the verification code has expired or is invalid
        if (cachedCode == null) {
            return "Verification code has expired or is invalid.";
        }

        // 验证码是否正确
        // Check if the verification code is correct
        if (!cachedCode.equals(code)) {
            return "Invalid verification code.";
        }

        // 验证成功，将验证状态标记为已验证
        // Verification successful, mark as verified
        redisService.markAsVerified(email);

        return "Email verified successfully!";
    }



    @PostMapping("/send-verification-code-phone")
    public String sendVerificationCodeToPhone(@RequestParam String phoneNumber) {
        // 查询是否有用户已注册该手机号并通过验证
        // Check if any user has already registered and verified this phone number
        User existingUser = userMapper.selectOne(new QueryWrapper<User>().eq("phone_number", phoneNumber));

        if (existingUser != null) {
            // 查询该用户的手机号验证状态
            // Check if the user's phone number verification status
            UserVerification phoneVerification = userVerificationMapper.selectOne(
                    new QueryWrapper<UserVerification>()
                            .eq("user_id", existingUser.getUserId())
                            .eq("verification_type", "phone")
            );

            // 如果手机号已被验证，拒绝发送验证码
            // If the phone number is already verified, reject sending verification code
            if (phoneVerification != null && "Verified".equals(phoneVerification.getVerificationStatus())) {
                return "Phone number is already registered and verified."; // 手机号已被注册且验证
            }
        }

        // 生成验证码
        // Generate a verification code
        String verificationCode = VerificationCodeGenerator.generateVerificationCode();

        // 将验证码保存到 Redis，有效期为5分钟
        // Store the verification code in Redis with a 5-minute expiration
        redisService.storeVerificationCode(phoneNumber, verificationCode);

        // 发送验证码到手机
        // Send the verification code to the phone
        smsService.sendSms(phoneNumber, "Your verification code is: " + verificationCode + ". This code will expire in 5 minutes.");

        return "Verification code sent successfully!";
    }


    @PostMapping("/verify-phone")
    public String verifyPhone(@RequestParam String phoneNumber, @RequestParam String code) {
        // 从 Redis 获取验证码
        // Get the verification code from Redis
        String cachedCode = redisService.getVerificationCode(phoneNumber);

        // 验证码是否过期或无效
        // Check if the verification code has expired or is invalid
        if (cachedCode == null) {
            return "Verification code has expired or is invalid."; // 验证码已过期或无效
        }

        // 验证码是否正确
        // Check if the verification code is correct
        if (!cachedCode.equals(code)) {
            return "Invalid verification code."; // 验证码不正确
        }

        // 验证成功，将验证状态标记为已验证
        // Verification successful, mark as verified
        redisService.markAsVerified(phoneNumber);

        return "Phone verified successfully!";
    }


    /**
     * 更新用户验证信息 (绑定新邮箱或手机号)
     * Update user verification information (bind new email or phone)
     */
    @PostMapping("/update-verification-sendcode")
    public String updateVerification(@RequestParam String userId,
                                     @RequestParam String newContact,
                                     @RequestParam String contactType) {

        // 检查新联系方式是否已被其他用户注册和验证
        // Check if the new contact is already registered and verified by another user
        if ("email".equals(contactType)) {
            User existingUser = userMapper.selectOne(new QueryWrapper<User>().eq("email", newContact));
            if (existingUser != null) {
                return "The email is already in use by another user.";
            }
        } else if ("phone".equals(contactType)) {
            User existingUserByPhone = userMapper.selectOne(new QueryWrapper<User>().eq("phone_number", newContact));
            if (existingUserByPhone != null) {
                return "The phone number is already in use by another user.";
            }
        }

        // 查询用户是否存在
        // Check if the user exists
        User user = userMapper.selectById(userId);
        if (user == null) {
            return "User does not exist.";
        }

        // 生成新的验证码
        // Generate a new verification code
        String verificationCode = VerificationCodeGenerator.generateVerificationCode();

        // 将验证码存储到 Redis 中，过期时间为5分钟
        // Store the verification code in Redis with an expiration time of 5 minutes
        redisService.storeVerificationCode(userId + ":" + contactType, verificationCode);

        // 根据联系方式类型发送验证码
        // Send verification code based on contact type
        if ("email".equals(contactType)) {
            emailService.sendVerificationEmail(newContact, verificationCode);
            return "Verification code has been sent to your new email.";
        } else if ("phone".equals(contactType)) {
            smsService.sendSms(newContact, "Your verification code is: " + verificationCode + ". This code will expire in 5 minutes.");
            return "Verification code has been sent to your new phone number.";
        }

        return "Invalid contact type.";
    }

    /**
     * 验证新的联系方式并更新
     * Verify the new contact information and update
     */
    @PostMapping("/verify-update")
    public String verifyUpdate(@RequestParam String userId,
                               @RequestParam String contactType,
                               @RequestParam String verificationCode,
                               @RequestParam String newContact) {

        // 从 Redis 获取验证码
        // Retrieve the verification code from Redis
        String storedCode = redisService.getVerificationCode(userId + ":" + contactType);

        if (storedCode == null) {
            return "Verification code expired or not found.";
        }

        // 验证用户输入的验证码是否正确
        // Check if the verification code matches
        if (!storedCode.equals(verificationCode)) {
            return "Invalid verification code.";
        }

        // 验证成功后，更新用户的联系方式
        // After successful verification, update the user's contact information
        User user = userMapper.selectById(userId);
        if ("email".equals(contactType)) {
            user.setEmail(newContact);
        } else if ("phone".equals(contactType)) {
            user.setPhoneNumber(newContact);
        }

        userMapper.updateById(user);

        // 检查是否已经有这个用户的验证记录
        // Check if the user already has a verification record for this type
        UserVerification existingVerification = userVerificationMapper.selectOne(
                new QueryWrapper<UserVerification>()
                        .eq("user_id", userId)
                        .eq("verification_type", contactType)
        );

        // 如果不存在，则插入新的验证记录
        // If no record exists, insert a new verification record
        if (existingVerification == null) {
            UserVerification newVerification = new UserVerification();
            newVerification.setVerificationId(UUID.randomUUID().toString());
            newVerification.setUserId(userId);
            newVerification.setVerificationType(contactType);
            newVerification.setVerificationStatus("Verified");
            newVerification.setInformation(newContact);
            newVerification.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
            newVerification.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
            userVerificationMapper.insert(newVerification);
        } else {
            // 如果记录存在，更新验证状态和新的联系方式
            // If record exists, update verification status and the new contact info
            existingVerification.setVerificationStatus("Verified");
            existingVerification.setInformation(newContact);
            existingVerification.setVerificationDate(Timestamp.valueOf(LocalDateTime.now()));
            existingVerification.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
            userVerificationMapper.updateById(existingVerification);
        }

        // 清除 Redis 中的验证码
        // Clear the verification code from Redis
        redisService.clearVerificationData(userId + ":" + contactType);

        return "Contact information updated successfully.";
    }

    @PostMapping("/verify-identity")
    public ResponseEntity<String> verifyIdentity(@RequestParam String userId) {
        // 查询是否存在相关的身份验证记录
        // Check if there is an existing identity verification record
        UserVerification existingVerification = userVerificationMapper.selectOne(
                new QueryWrapper<UserVerification>()
                        .eq("user_id", userId)
                        .eq("verification_type", "Identity")
        );

        if (existingVerification == null) {
            // 插入新的验证记录
            // Insert a new verification record
            UserVerification newVerification = new UserVerification();
            newVerification.setVerificationId(UUID.randomUUID().toString());
            newVerification.setUserId(userId);
            newVerification.setVerificationType("Identity");
            newVerification.setVerificationStatus("Verified");
            newVerification.setVerificationDate(Timestamp.valueOf(LocalDateTime.now()));
            newVerification.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
            newVerification.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
            userVerificationMapper.insert(newVerification);
        } else {
            // 更新已有的验证记录
            // Update the existing verification record
            existingVerification.setVerificationStatus("Verified");
            existingVerification.setVerificationDate(Timestamp.valueOf(LocalDateTime.now()));
            existingVerification.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
            userVerificationMapper.updateById(existingVerification);
        }

        return ResponseEntity.ok("Verification processed successfully.");
    }

    @GetMapping("/check-identity")
    public ResponseEntity<Map<String, Boolean>> checkIdentityVerification(@RequestParam String userId) {
        UserVerification verification = userVerificationMapper.selectOne(
                new QueryWrapper<UserVerification>()
                        .eq("user_id", userId)
                        .eq("verification_type", "Identity")
                        .eq("verification_status", "Verified")
        );

        Map<String, Boolean> response = new HashMap<>();
        response.put("verified", verification != null);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/verifications/getVerifyStatus")
    public String getAccessToken(@RequestParam("code") String grantCode) {
        logger.info("Grant Code: " + grantCode);

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(clientId, clientSecret);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);


        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("code", grantCode);
        body.add("redirect_uri", "https://digitalid-sandbox.com/oauth2/echo");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(authUrl, request, String.class);
            logger.info("Response: " + response.getBody());


            if (response.getStatusCode().is2xxSuccessful()) {
                JSONObject jsonResponse = new JSONObject(response.getBody());
                return jsonResponse.getString("id_token");
            } else {
                System.out.println("Error: " + response.getBody());
                return null;
            }
        } catch (HttpClientErrorException e) {
            System.out.println("Error: " + e.getResponseBodyAsString());
            return null;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }











}
