package com.example.Neighborhood_Walk.controller;

import com.example.Neighborhood_Walk.Mapper.MeetingMapper;
import com.example.Neighborhood_Walk.entity.PreMeeting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/PreMeeting")
public class MeetingController {
    @Autowired
    private MeetingMapper MeetingMapper;

    // 创建新的 PreMeeting
    @PostMapping("/createPreMeeting")
    public String createPreMeeting(@RequestBody PreMeeting preMeeting, String applicationId) {
        // 设置 applicationId（主键）
        preMeeting.setApplicationId(applicationId);
        MeetingMapper.insert(preMeeting);
        return "Create Successfully";
    }

    // 根据 ID 获取现有的 PreMeeting
    @GetMapping("/{id}")
    public PreMeeting getPreMeetingById(@PathVariable String id) {
        PreMeeting preMeeting = MeetingMapper.findById(id);
        if (preMeeting != null) {
            return preMeeting;
        } else {
            return null;
        }
    }

    // 更新现有的 PreMeeting
    @PutMapping("/{id}/edit")
    public String updatePreMeeting(@PathVariable String id, @RequestBody PreMeeting updatedPreMeeting) {
        PreMeeting existingPreMeeting = MeetingMapper.findById(id);
        if (existingPreMeeting != null) {
            updatedPreMeeting.setApplicationId(existingPreMeeting.getApplicationId());
            // 确保 ID 保持不变
            MeetingMapper.update(updatedPreMeeting);
            return "Update Successfully";
        } else {
            return "PreMeeting not found";
        }
    }
}
