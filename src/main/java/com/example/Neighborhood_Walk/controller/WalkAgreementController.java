package com.example.Neighborhood_Walk.controller;

import com.example.Neighborhood_Walk.entity.WalkAgreement;
import com.example.Neighborhood_Walk.Mapper.WalkAgreementMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/agreements")
public class WalkAgreementController {

    @Autowired
    private WalkAgreementMapper walkAgreementMapper;

    // 创建新的 WalkAgreement
    @PostMapping("/create")
    public String createWalkAgreement(@RequestBody WalkAgreement walkAgreement) {
        walkAgreement.setAgreementId(UUID.randomUUID().toString());
        walkAgreementMapper.insert(walkAgreement);
        return "WalkAgreement created successfully!";
    }

    // 获取特定 WalkAgreement
    @GetMapping("/{id}")
    public WalkAgreement getWalkAgreementById(@PathVariable("id") String agreementId) {
        return walkAgreementMapper.selectById(agreementId);
    }

    // 获取所有 WalkAgreement
    @GetMapping("/all")
    public List<WalkAgreement> getAllWalkAgreements() {
        return walkAgreementMapper.selectList(null);
    }

    // 更新 WalkAgreement
    @PutMapping("/{id}")
    public String updateWalkAgreement(@PathVariable("id") String agreementId, @RequestBody WalkAgreement walkAgreement) {
        WalkAgreement existingAgreement = walkAgreementMapper.selectById(agreementId);
        if (existingAgreement == null) {
            return "WalkAgreement not found.";
        }
        walkAgreement.setAgreementId(agreementId);
        walkAgreementMapper.updateById(walkAgreement);
        return "WalkAgreement updated successfully!";
    }

    // 删除 WalkAgreement（需要管理员权限）
    @DeleteMapping("/{id}")
    public String deleteWalkAgreement(@PathVariable("id") String agreementId, @RequestHeader("userType") String userType) {
        if (!"Admin".equalsIgnoreCase(userType)) {
            return "You do not have permission to delete WalkAgreements.";
        }

        int result = walkAgreementMapper.deleteById(agreementId);
        if (result > 0) {
            return "WalkAgreement deleted successfully!";
        } else {
            return "WalkAgreement not found.";
        }
    }
}