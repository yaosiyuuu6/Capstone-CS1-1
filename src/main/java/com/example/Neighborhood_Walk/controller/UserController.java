package com.example.Neighborhood_Walk.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.Neighborhood_Walk.Mapper.AddressMapper;
import com.example.Neighborhood_Walk.Mapper.UserMapper;
import com.example.Neighborhood_Walk.Mapper.UserVerificationMapper;
import com.example.Neighborhood_Walk.dto.UserDto;

import com.example.Neighborhood_Walk.entity.Address;
import com.example.Neighborhood_Walk.entity.User;
import com.example.Neighborhood_Walk.entity.UserVerification;
import com.example.Neighborhood_Walk.service.AddressService;
import com.example.Neighborhood_Walk.service.RedisService;
import com.example.Neighborhood_Walk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Comparator;
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

    @Autowired
    private UserService userService;

    @Autowired
    private AddressService addressService;

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

        return "User registered successfully! UserID: " + user.getUserId();
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

    @GetMapping("/nearby-walkers")
    public ResponseEntity<List<UserDto>> getNearbyWalkers(
            @RequestParam String userId,
            @RequestParam double rangeInKm) {

        // 1. 获取用户的地址经纬度
        Address parentAddress = userService.getUserAddressByUserId(userId);
        if (parentAddress == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        // 2. 在地址表中根据距离筛选出符合要求的地址ID
        List<String> nearbyAddressIds = addressService.getNearbyAddressIds(
                parentAddress.getLatitude(),
                parentAddress.getLongitude(),
                rangeInKm
        );


        // 3. 在用户表中找到类型为walker并且匹配地址ID的用户
        List<UserDto> nearbyWalkers = userMapper.findWalkersByAddressIds(nearbyAddressIds, parentAddress.getLatitude().doubleValue(),
                parentAddress.getLongitude().doubleValue());


        // 按距离从近到远排序
        nearbyWalkers.sort(Comparator.comparingDouble(UserDto::getDistance));

        return ResponseEntity.ok(nearbyWalkers);
    }

    @GetMapping("/nearby-parents")
    public ResponseEntity<List<UserDto>> getNearbyParents(
            @RequestParam String userId,
            @RequestParam double rangeInKm) {

        // 1. 获取用户的地址经纬度
        Address walkerAddress = userService.getUserAddressByUserId(userId);
        if (walkerAddress == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        // 2. 在地址表中根据距离筛选出符合要求的地址ID
        List<String> nearbyAddressIds = addressService.getNearbyAddressIds(
                walkerAddress.getLatitude(),
                walkerAddress.getLongitude(),
                rangeInKm
        );


        // 3. 在用户表中找到类型为walker并且匹配地址ID的用户
        List<UserDto> nearbyParents = userMapper.findParentsByAddressIds(nearbyAddressIds, walkerAddress.getLatitude().doubleValue(),
                walkerAddress.getLongitude().doubleValue());


        // 按距离从近到远排序
        nearbyParents.sort(Comparator.comparingDouble(UserDto::getDistance));

        return ResponseEntity.ok(nearbyParents);
    }
}
