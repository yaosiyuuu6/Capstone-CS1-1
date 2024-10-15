package com.example.Neighborhood_Walk.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.Neighborhood_Walk.Mapper.WalkerProfileMapper;
import com.example.Neighborhood_Walk.dto.WalkerProfileDTO;
import com.example.Neighborhood_Walk.entity.WalkerProfile;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.Map;

@RestController
@RequestMapping("/profile")
public class WalkerProfileController {

    @Autowired
    private WalkerProfileMapper walkerProfileMapper;


    private static final ObjectMapper objectMapper = new ObjectMapper();

    // 将 Map 转换为 JSON 字符串
    private String convertMapToJson(Map<String, Object> map) throws JsonProcessingException {
        return objectMapper.writeValueAsString(map);
    }

    // 将 JSON 字符串转换为 Map
    private Map<String, Object> convertJsonToMap(String json) throws JsonProcessingException {
        return objectMapper.readValue(json, Map.class);
    }

    // 创建或更新 WalkerProfile
    @PostMapping("/saveProfile")
    public String saveProfile(@RequestBody WalkerProfileDTO profileDTO) throws JsonProcessingException {
        // 尝试查找现有的 WalkerProfile
        WalkerProfile existingProfile = walkerProfileMapper.selectOne(
                new QueryWrapper<WalkerProfile>().eq("walker_id", profileDTO.getWalkerId())
        );

        // 如果没有找到 profile，则新建，否则更新现有 profile
        WalkerProfile profile = (existingProfile != null) ? existingProfile : new WalkerProfile();
        profile.setWalkerId(profileDTO.getWalkerId());
        profile.setSkills(profileDTO.getSkills());

        // 根据前端传递的数据判断存储逻辑
        if (profileDTO.getAvailableDatesTimes() != null) {
            // 如果传递了 availableDatesTimes，说明是 Weekly 时间表
            profile.setAvailableDatesTimes(convertMapToJson(profileDTO.getAvailableDatesTimes()));
            profile.setAvailableDate(null);  // 清空 One-off 相关字段
            profile.setTimePeriod(null);
        } else if (profileDTO.getAvailableDate() != null && profileDTO.getTimePeriod() != null) {
            // 如果传递了 availableDate 和 timePeriod，说明是 One-off 时间表
            profile.setAvailableDatesTimes(null);  // 清空 Weekly 相关字段
            profile.setAvailableDate(profileDTO.getAvailableDate());
            profile.setTimePeriod(profileDTO.getTimePeriod());
        } else {
            // 如果既没有传 availableDatesTimes 也没有传 availableDate，返回错误
            return "Invalid request: missing time-related data";
        }

        // 插入或更新数据库
        if (existingProfile == null) {
            walkerProfileMapper.insert(profile);
            return "Profile created successfully";
        } else {
            walkerProfileMapper.updateById(profile);
            return "Profile updated successfully";
        }
    }

    // 获取 WalkerProfile
    @GetMapping("/{id}")
    public WalkerProfile getProfileById(@PathVariable String id) throws JsonProcessingException {
        WalkerProfile profile = walkerProfileMapper.selectOne(new QueryWrapper<WalkerProfile>().eq("walker_id", id));

        if (profile != null) {
            // 将 JSON 字符串转换回 Map
            if (profile.getAvailableDatesTimes() != null) {
                Map<String, Object> availableDatesTimes = convertJsonToMap(profile.getAvailableDatesTimes());
                profile.setAvailableDatesTimes(availableDatesTimes.toString());
            }
            return profile;
        } else {
            return null;
        }
    }
}
