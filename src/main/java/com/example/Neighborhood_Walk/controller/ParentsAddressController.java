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

    // 创建新的 ParentsAddress 记录
    @PostMapping("/create")
    public String createParentsAddress(@RequestBody ParentsAddress parentsAddress) {
        int result = parentsAddressMapper.insert(parentsAddress);
        if (result > 0) {
            return "ParentsAddress created successfully!";
        } else {
            return "Failed to create ParentsAddress.";
        }
    }

    // 根据 userId 获取 ParentsAddress 记录
    @GetMapping("/{userId}")
    public List<ParentsAddress> getParentsAddressByUserId(@PathVariable String userId) {
        QueryWrapper<ParentsAddress> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userId", userId);
        return parentsAddressMapper.selectList(queryWrapper);
    }

    // 更新 ParentsAddress 记录
    @PutMapping("/update")
    public String updateParentsAddress(@RequestBody ParentsAddress parentsAddress) {
        int result = parentsAddressMapper.updateById(parentsAddress);
        if (result > 0) {
            return "ParentsAddress updated successfully!";
        } else {
            return "Failed to update ParentsAddress.";
        }
    }

    // 删除 ParentsAddress 记录
    @DeleteMapping("/delete/{userId}/{addressId}")
    public String deleteParentsAddress(@PathVariable String userId, @PathVariable String addressId) {
        QueryWrapper<ParentsAddress> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userId", userId).eq("addressId", addressId);
        int result = parentsAddressMapper.delete(queryWrapper);
        if (result > 0) {
            return "ParentsAddress deleted successfully!";
        } else {
            return "Failed to delete ParentsAddress.";
        }
    }
}
