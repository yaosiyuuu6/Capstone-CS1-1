package com.example.Neighborhood_Walk.controller;

import com.example.Neighborhood_Walk.entity.Application;
import com.example.Neighborhood_Walk.Mapper.ApplicationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/applications")
public class ApplicationController {

    @Autowired
    private ApplicationMapper applicationMapper;

    // 创建新的申请
    @PostMapping("/create")
    public String createApplication(@RequestBody Application application) {
        application.setApplicationId(UUID.randomUUID().toString());
        applicationMapper.insert(application);
        return "Application created successfully!";
    }

    // 获取特定申请
    @GetMapping("/{id}")
    public Application getApplicationById(@PathVariable("id") String applicationId) {
        return applicationMapper.selectById(applicationId);
    }
    
    //根据walker获取申请
    @GetMapping("/byWalker/{walker_id}")
    public List<Application> getApplicationByWalker(@PathVariable("walker_id") String walker_id) {
        System.out.println(walker_id);
        System.out.println(applicationMapper.findByWalker(walker_id));
        return applicationMapper.findByWalker(walker_id);
    }

    //根据request获取申请
    @GetMapping("/byRequest/{request_id}")
    public List<Application> getApplicationByRequest(@PathVariable("request_id") String request_id) {
        return applicationMapper.findByRequest(request_id);
    }
    
    // 获取所有申请
    @GetMapping("/all")
    public List<Application> getAllApplications() {
        return applicationMapper.selectList(null);
    }

    // 更新申请
    @PutMapping("/{id}")
    public String updateApplication(@PathVariable("id") String applicationId, @RequestBody Application application) {
        Application existingApplication = applicationMapper.selectById(applicationId);
        if (existingApplication == null) {
            return "Application not found.";
        }
        application.setApplicationId(applicationId);
        applicationMapper.updateById(application);
        return "Application updated successfully!";
    }

    // 删除申请（需要管理员权限）
    @DeleteMapping("/{id}")
    public String deleteApplication(@PathVariable("id") String applicationId, @RequestHeader("userType") String userType) {
        if (!"Admin".equalsIgnoreCase(userType)) {
            return "You do not have permission to delete applications.";
        }

        int result = applicationMapper.deleteById(applicationId);
        if (result > 0) {
            return "Application deleted successfully!";
        } else {
            return "Application not found.";
        }
    }
}
