package com.example.Neighborhood_Walk.controller;

// src/main/java/com/yourpackage/controller/ShareRequestController.java



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.Neighborhood_Walk.dto.ShareRequestWithDescription;
import com.example.Neighborhood_Walk.entity.ShareRequest;
import com.example.Neighborhood_Walk.Mapper.ShareRequestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/share-requests")
public class ShareRequestController {

    @Autowired
    private ShareRequestMapper shareRequestMapper;

    // 创建 ShareRequest
    @PostMapping
    public ResponseEntity<ShareRequest> createShareRequest(@RequestBody ShareRequest shareRequest) {
        // 后端生成 UUID
        shareRequest.setId(UUID.randomUUID().toString());  // 设置主键 ID
        shareRequest.setRequestId(UUID.randomUUID().toString());  // 设置 requestId
        shareRequest.setParentId(UUID.randomUUID().toString());  // 设置 parentId
        shareRequest.setWalkerId(UUID.randomUUID().toString());  // 设置 walkerId
        shareRequest.setShareStatus("Pending");  // 设置默认状态为 Pending
        shareRequest.setCreatedAt(LocalDateTime.now());
        shareRequest.setUpdatedAt(LocalDateTime.now());

        int rows = shareRequestMapper.insert(shareRequest);
        if (rows > 0) {
            return ResponseEntity.ok(shareRequest);
        } else {
            return ResponseEntity.status(500).build();
        }
    }


    // 获取所有 ShareRequests
    @GetMapping
    public ResponseEntity<List<ShareRequest>> getAllShareRequests() {
        List<ShareRequest> list = shareRequestMapper.selectList(null);
        return ResponseEntity.ok(list);
    }

    // 根据 ID 获取 ShareRequest
    @GetMapping("/{id}")
    public ResponseEntity<ShareRequest> getShareRequestById(@PathVariable Long id) {
        ShareRequest shareRequest = shareRequestMapper.selectById(id);
        if (shareRequest != null) {
            return ResponseEntity.ok(shareRequest);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 根据 parentId 获取 ShareRequests
    @GetMapping("/by-parent/{parentId}")
    public ResponseEntity<List<ShareRequest>> getShareRequestsByParentId(@PathVariable String parentId) {
        QueryWrapper<ShareRequest> query = new QueryWrapper<>();
        query.eq("parentId", parentId);
        List<ShareRequest> list = shareRequestMapper.selectList(query);
        return ResponseEntity.ok(list);
    }

    // 根据 walkerId 获取 ShareRequests
    @GetMapping("/by-walker/{walkerId}")
    public ResponseEntity<List<ShareRequestWithDescription>> getShareRequestsByWalkerId(@PathVariable String walkerId) {
        List<ShareRequestWithDescription> list = shareRequestMapper.getRequestsByWalkerIdWithDescription(walkerId);
        return ResponseEntity.ok(list);
    }

    // 更新 ShareRequest 的 shareStatus
    @PutMapping("/{id}/status")
    public ResponseEntity<String> updateShareStatus(@PathVariable Long id, @RequestParam String shareStatus) {
        ShareRequest shareRequest = shareRequestMapper.selectById(id);
        if (shareRequest == null) {
            return ResponseEntity.notFound().build();
        }

        // 更新状态
        shareRequest.setShareStatus(shareStatus);
        int rows = shareRequestMapper.updateById(shareRequest);
        if (rows > 0) {
            return ResponseEntity.ok("Share status updated successfully.");
        } else {
            return ResponseEntity.status(500).body("Failed to update share status.");
        }
    }

    // 删除 ShareRequest
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteShareRequest(@PathVariable Long id) {
        int rows = shareRequestMapper.deleteById(id);
        if (rows > 0) {
            return ResponseEntity.ok("ShareRequest deleted successfully.");
        } else {
            return ResponseEntity.status(500).body("Failed to delete ShareRequest.");
        }
    }
}

