package com.example.Neighborhood_Walk.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.Neighborhood_Walk.entity.ParentsAddress;
import com.example.Neighborhood_Walk.Mapper.ParentsAddressMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/parentsAddress")
public class ParentsAddressController {

    @Autowired
    private ParentsAddressMapper parentsAddressMapper;

    // Create a new ParentsAddress record
    @PostMapping("/create")
    public String createParentsAddress(@RequestBody ParentsAddress parentsAddress) {
        // Insert the new ParentsAddress record into the database
        int result = parentsAddressMapper.insert(parentsAddress);
        if (result > 0) {
            return "ParentsAddress created successfully!";
        } else {
            return "Failed to create ParentsAddress.";
        }
    }

    // Get ParentsAddress records by userId
    @GetMapping("/{userId}")
    public List<ParentsAddress> getParentsAddressByUserId(@PathVariable String userId) {
        // Create a QueryWrapper to filter by userId
        QueryWrapper<ParentsAddress> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_Id", userId);

        // Retrieve the list of ParentsAddress records that match the userId
        return parentsAddressMapper.selectList(queryWrapper);
    }

    // Update an existing ParentsAddress record
    @PutMapping("/update")
    public String updateParentsAddress(@RequestBody ParentsAddress parentsAddress) {
        // Update the existing ParentsAddress record in the database
        int result = parentsAddressMapper.updateById(parentsAddress);
        if (result > 0) {
            return "ParentsAddress updated successfully!";
        } else {
            return "Failed to update ParentsAddress.";
        }
    }

    // Delete a ParentsAddress record by userId and addressId
    @DeleteMapping("/delete/{userId}/{addressId}")
    public String deleteParentsAddress(@PathVariable String userId, @PathVariable String addressId) {
        // Create a QueryWrapper to filter by both userId and addressId
        QueryWrapper<ParentsAddress> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId).eq("address_id", addressId);

        // Delete the ParentsAddress record matching both userId and addressId
        int result = parentsAddressMapper.delete(queryWrapper);
        if (result > 0) {
            return "ParentsAddress deleted successfully!";
        } else {
            return "Failed to delete ParentsAddress.";
        }
    }

}
