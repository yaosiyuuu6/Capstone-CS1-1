package com.example.Neighborhood_Walk.controller;

import com.example.Neighborhood_Walk.Mapper.AddressMapper;
import com.example.Neighborhood_Walk.Mapper.UserMapper;
import com.example.Neighborhood_Walk.entity.Address;
import com.example.Neighborhood_Walk.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AddressMapper addressMapper;

    @PostMapping("/register")
    public String registerUser(@RequestBody User user) {
        // check email and password not null
        if (user.getEmail() == null || user.getPassword() == null) {
            return "Email and password are required.";
        }

        // check email registered
        User existingUser = userMapper.findByEmail(user.getEmail());
        if (existingUser != null) {
            return "Email is already registered.";
        }

        // check addressId exist
        Address existingAddress = addressMapper.selectById(user.getAddressId());
        if (existingAddress == null) {
            return "Address not saved. Please save the address first.";
        }
        // set userid
        user.setUserId(UUID.randomUUID().toString());

        // save data into database
        userMapper.insert(user);
        return "User registered successfully!";
    }

    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userMapper.selectList(null);  // selectList(null) 将返回所有用户
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


}
