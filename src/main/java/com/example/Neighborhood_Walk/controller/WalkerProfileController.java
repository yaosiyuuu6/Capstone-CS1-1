package com.example.Neighborhood_Walk.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.Neighborhood_Walk.Mapper.WalkerProfileMapper;
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

    // 创建新的 Profile
    @PostMapping("/createProfile")
    public String createProfile(@RequestBody WalkerProfile profile,
                                @RequestParam String id,
                                @RequestBody Map<String, Object> availableDatesTimes,
                                @RequestBody Map<String, Object> skills) throws JsonProcessingException {
        // 将 Map 转换为 JSON 字符串存储
        profile.setWalkerId(id);
        profile.setAvailableDatesTimes(convertMapToJson(availableDatesTimes));
        profile.setSkills(convertMapToJson(skills));

        walkerProfileMapper.insert(profile);
        return "Profile created successfully";
    }

    // 根据 ID 获取 Profile
    @GetMapping("/{id}")
    public WalkerProfile getProfileById(@PathVariable String id) throws JsonProcessingException {
        WalkerProfile profile = walkerProfileMapper.selectOne(new QueryWrapper<WalkerProfile>().eq("walker_id", id));

        if (profile != null) {
            // 将 JSON 字符串转换回 Map
            Map<String, Object> availableDatesTimes = convertJsonToMap(profile.getAvailableDatesTimes());
            Map<String, Object> skills = convertJsonToMap(profile.getSkills());

            // 可以在这里返回 profile，也可以处理 Map 的数据
            return profile;
        } else {
            return null;
        }
    }

    // 更新 Profile
    @PutMapping("/{id}/edit")
    public String updateProfile(@PathVariable String id,
                                @RequestBody WalkerProfile updatedProfile,
                                @RequestBody Map<String, Object> availableDatesTimes,
                                @RequestBody Map<String, Object> skills) throws JsonProcessingException {
        WalkerProfile existingProfile = walkerProfileMapper.selectOne(new QueryWrapper<WalkerProfile>().eq("walker_id", id));

        if (existingProfile != null) {
            // 保证 walkerId 不变
            updatedProfile.setWalkerId(existingProfile.getWalkerId());

            // 将 Map 转换为 JSON 字符串存储
            updatedProfile.setAvailableDatesTimes(convertMapToJson(availableDatesTimes));
            updatedProfile.setSkills(convertMapToJson(skills));

            walkerProfileMapper.updateById(updatedProfile);
            return "Profile updated successfully";
        } else {
            return "Profile not found";
        }
    }
}
