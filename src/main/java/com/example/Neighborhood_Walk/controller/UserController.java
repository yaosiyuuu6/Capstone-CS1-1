package com.example.Neighborhood_Walk.controller;

import com.example.Neighborhood_Walk.Mapper.AddressMapper;
import com.example.Neighborhood_Walk.Mapper.UserMapper;
import com.example.Neighborhood_Walk.Mapper.UserVerificationMapper;
import com.example.Neighborhood_Walk.entity.Address;
import com.example.Neighborhood_Walk.entity.User;
import com.example.Neighborhood_Walk.entity.UserVerification;
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

    @PostMapping("/register")
    public String registerUser(@RequestBody User user) {
        // Check that at least one of email or phone number is provided
        if ((user.getEmail() == null || user.getEmail().isEmpty()) &&
                (user.getPhoneNumber() == null || user.getPhoneNumber().isEmpty())) {
            return "Email or phone number is required.";
        }

        // Check if the password is provided
        if (user.getPassword() == null) {
            return "Password is required.";
        }

        // Check if email is already registered and verified
        if (user.getEmail() != null) {
            User existingUser = userMapper.findByEmail(user.getEmail());
            if (existingUser != null) {
                UserVerification emailVerification = userVerificationMapper.findByUserIdAndType(existingUser.getUserId(), "email");
                if (emailVerification != null && "Verified".equals(emailVerification.getVerificationStatus())) {
                    return "Email is already registered and verified.";
                }
            }
        }

        // Check if phone is already registered and verified
        if (user.getPhoneNumber() != null) {
            User existingUserByPhone = userMapper.findByPhone(user.getPhoneNumber());
            if (existingUserByPhone != null) {
                UserVerification phoneVerification = userVerificationMapper.findByUserIdAndType(existingUserByPhone.getUserId(), "phone");
                if (phoneVerification != null && "Verified".equals(phoneVerification.getVerificationStatus())) {
                    return "Phone is already registered and verified.";
                }
            }
        }


        // Check if the addressId exists
        Address existingAddress = addressMapper.selectById(user.getAddressId());
        if (existingAddress == null) {
            return "Address not saved. Please save the address first.";
        }

        // Set user ID
        user.setUserId(UUID.randomUUID().toString());

        // Save user data to the database
        userMapper.insert(user);

        // Email verification logic
        if (user.getEmail() != null) {
            UserVerification emailVerification = new UserVerification();
            emailVerification.setVerificationId(UUID.randomUUID().toString());
            emailVerification.setUserId(user.getUserId());
            emailVerification.setVerificationType("email");
            emailVerification.setVerificationStatus("Unverified");
            emailVerification.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
            emailVerification.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
            userVerificationMapper.insert(emailVerification);
        }

        // Phone verification logic
        if (user.getPhoneNumber() != null) {
            UserVerification phoneVerification = new UserVerification();
            phoneVerification.setVerificationId(UUID.randomUUID().toString());
            phoneVerification.setUserId(user.getUserId());
            phoneVerification.setVerificationType("phone");
            phoneVerification.setVerificationStatus("Unverified");
            phoneVerification.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
            phoneVerification.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
            userVerificationMapper.insert(phoneVerification);
        }

        return "User registered successfully. Email and/or phone verification pending.";
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

    @PostMapping("/emailverify")
    public String verifyUser(@RequestParam String email, @RequestParam String code) {
        String storedCode = userMapper.getVerificationCodeByEmail(email);
        if (storedCode != null && storedCode.equals(code)) {
            userMapper.updateUserStatus(email, true);
            return "验证成功！";
        } else {
            return "验证失败，验证码错误。";
        }
    }


}
