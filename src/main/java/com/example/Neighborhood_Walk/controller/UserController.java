package com.example.Neighborhood_Walk.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.Neighborhood_Walk.Mapper.AddressMapper;
import com.example.Neighborhood_Walk.Mapper.UserMapper;
import com.example.Neighborhood_Walk.Mapper.UserVerificationMapper;
import com.example.Neighborhood_Walk.entity.Address;
import com.example.Neighborhood_Walk.entity.User;
import com.example.Neighborhood_Walk.entity.UserVerification;
import com.example.Neighborhood_Walk.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AddressMapper addressMapper;

    @Autowired
    private UserVerificationMapper userVerificationMapper;

    @Autowired
    private RedisService redisService;

    @PostMapping("/register")
    public String registerUser(@RequestBody User user) {
        // 检查至少一种联系方式已验证
        // Ensure at least one contact method is verified
        if (!Boolean.TRUE.equals(redisService.isVerified(user.getEmail())) &&
                !Boolean.TRUE.equals(redisService.isVerified(user.getPhoneNumber()))) {
            return "At least one contact method must be verified."; // 至少一种联系方式需验证
        }

        // 检查密码是否提供
        // Check if the password is provided
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            return "Password is required."; // 需要提供密码
        }

//        // 检查地址是否存在
//        // Check if the address exists
//        Address existingAddress = addressMapper.selectById(user.getAddressId());
//        if (existingAddress == null) {
//            return "Address not saved. Please save the address first."; // 地址未保存
//        }

        // 生成用户ID并保存用户信息
        // Generate user ID and save user information
        user.setUserId(UUID.randomUUID().toString());
        userMapper.insert(user);

        // 持久化用户验证记录到数据库
        // Persist user verification records to the database
        if (Boolean.TRUE.equals(redisService.isVerified(user.getEmail()))) {
            UserVerification emailVerification = new UserVerification();
            emailVerification.setVerificationId(UUID.randomUUID().toString());
            emailVerification.setUserId(user.getUserId());
            emailVerification.setVerificationType("email");
            emailVerification.setVerificationStatus("Verified");
            emailVerification.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
            emailVerification.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
            userVerificationMapper.insert(emailVerification);
        }

        if (Boolean.TRUE.equals(redisService.isVerified(user.getPhoneNumber()))) {
            UserVerification phoneVerification = new UserVerification();
            phoneVerification.setVerificationId(UUID.randomUUID().toString());
            phoneVerification.setUserId(user.getUserId());
            phoneVerification.setVerificationType("phone");
            phoneVerification.setVerificationStatus("Verified");
            phoneVerification.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
            phoneVerification.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
            userVerificationMapper.insert(phoneVerification);
        }

        // 清除 Redis 中的验证状态
        // Clear the verification data from Redis
        redisService.clearVerificationData(user.getEmail());
        redisService.clearVerificationData(user.getPhoneNumber());

        return "User registered successfully!";
    }



    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userMapper.selectList(null);  // selectList(null) 将返回所有用户
    }

    // get all walkers
    @GetMapping("/get_allwalker")
    public List<User> getAllWalkers() {
        return userMapper.getAllWalkers();
    }

    // get all parents
    @GetMapping("/get_allparent")
    public List<User> getAllParents() {
        return userMapper.getAllParents();
    }

    // get all admins
    @GetMapping("/get_alladmin")
    public List<User> getAllAdmins() {
        return userMapper.getAllAdmins();
    }

    // get all users with both walker and parent
    @GetMapping("/get_allboth")
    public List<User> getAllBoth() {
        return userMapper.getAllBoth();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable("id") String userId) {
        return userMapper.selectById(userId);
    }

    // select user by address_id
    @GetMapping("/user_addressid/{id}")
    public User getUserByAddressId(@PathVariable("id") String addressId) {
        return userMapper.findByAddressId(addressId);
    }




    @GetMapping("/address/{id}")
    public Address getAddressById(@PathVariable("id") String addressId) {
        return addressMapper.selectById(addressId);
    }

    @PutMapping("/{id}")
    public String updateUser(@PathVariable("id") String userId, @RequestBody User user) {
        User existingUser = userMapper.selectById(userId);
        if (existingUser == null) {
            return "User not found.";
        }
        user.setUserId(userId);
        userMapper.updateById(user);
        return "User updated successfully!";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") String userId) {
        int result = userMapper.deleteById(userId);
        if (result > 0) {
            return "User deleted successfully!";
        } else {
            return "User not found.";
        }
    }


    /**
     * 登录接口
     * Login API
     */
    @PostMapping("/login")
    public String login(@RequestParam String account, @RequestParam String password) {
        // 判断账号是邮箱还是手机号
        // Determine whether the account is an email or phone number
        User user = null;
        if (account.contains("@")) {
            // 通过邮箱登录
            // Login using email
            user = userMapper.selectOne(new QueryWrapper<User>().eq("email", account));
        } else {
            // 假设手机号是澳大利亚号码，格式为"04"开头，后跟8位数字
            // Assume the phone number is Australian, starting with "04" and followed by 8 digits
            if (account.matches("^04\\d{8}$")) {
                // 通过手机号登录
                // Login using phone number
                user = userMapper.selectOne(new QueryWrapper<User>().eq("phone_number", account));
            } else {
                return "Invalid phone number format. Please use a valid Australian phone number.";
            }
        }

        // 如果用户不存在
        // If the user does not exist
        if (user == null) {
            return "User not found.";
        }

        // 检查密码是否匹配
        // Check if the password matches
        if (!user.getPassword().equals(password)) {
            return "Incorrect password.";
        }

        // 登录成功，返回用户ID
        // Login successful, return the user ID
        return "Login successful. User ID: " + user.getUserId();
    }




}
