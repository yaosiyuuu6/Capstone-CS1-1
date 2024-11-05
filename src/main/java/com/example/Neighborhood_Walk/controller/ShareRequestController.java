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

    // Create a new ShareRequest
    @PostMapping
    public ResponseEntity<ShareRequest> createShareRequest(@RequestBody ShareRequest shareRequest) {
        // Generate a UUID for the ShareRequest's primary key
        shareRequest.setId(UUID.randomUUID().toString());

        // Set the initial status to 'Pending'
        shareRequest.setShareStatus("Pending");

        // Set the created and updated timestamps
        shareRequest.setCreatedAt(LocalDateTime.now());
        shareRequest.setUpdatedAt(LocalDateTime.now());

        // Insert the ShareRequest into the database
        int rows = shareRequestMapper.insert(shareRequest);

        // Return the ShareRequest if the insertion was successful, else return a 500 status
        if (rows > 0) {
            return ResponseEntity.ok(shareRequest);
        } else {
            return ResponseEntity.status(500).build();
        }
    }

    // Get all ShareRequests
    @GetMapping
    public ResponseEntity<List<ShareRequest>> getAllShareRequests() {
        // Retrieve all ShareRequest records from the database
        List<ShareRequest> list = shareRequestMapper.selectList(null);

        // Return the list of ShareRequests
        return ResponseEntity.ok(list);
    }

    // Get a specific ShareRequest by its ID
    @GetMapping("/{id}")
    public ResponseEntity<ShareRequest> getShareRequestById(@PathVariable Long id) {
        // Retrieve the ShareRequest by its ID
        ShareRequest shareRequest = shareRequestMapper.selectById(id);

        // Return the ShareRequest if found, else return a 404 status
        if (shareRequest != null) {
            return ResponseEntity.ok(shareRequest);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Get ShareRequests by parentId
    @GetMapping("/by-parent/{parentId}")
    public ResponseEntity<List<ShareRequest>> getShareRequestsByParentId(@PathVariable String parentId) {
        // Create a query to filter ShareRequests by parentId
        QueryWrapper<ShareRequest> query = new QueryWrapper<>();
        query.eq("parentId", parentId);

        // Retrieve the list of ShareRequests matching the parentId
        List<ShareRequest> list = shareRequestMapper.selectList(query);

        // Return the list of ShareRequests
        return ResponseEntity.ok(list);
    }

    // Get ShareRequests by walkerId
    @GetMapping("/by-walker/{walkerId}")
    public ResponseEntity<List<ShareRequest>> getShareRequestsByWalkerId(@PathVariable String walkerId) {
        // Create a query to filter ShareRequests by walkerId
        QueryWrapper<ShareRequest> query = new QueryWrapper<>();
        query.eq("walkerId", walkerId);

        // Retrieve the list of ShareRequests matching the walkerId
        List<ShareRequest> list = shareRequestMapper.selectList(query);

        // Return the list of ShareRequests
        return ResponseEntity.ok(list);
    }

    // Update the shareStatus of an existing ShareRequest
    @PutMapping("/updateStatus/{id}")
    public ResponseEntity<String> updateShareStatus(@PathVariable String id, @RequestParam String shareStatus) {
        // Retrieve the ShareRequest by its ID
        ShareRequest shareRequest = shareRequestMapper.selectById(id);

        // If the ShareRequest is not found, return a 404 status
        if (shareRequest == null) {
            return ResponseEntity.notFound().build();
        }

        // Update the shareStatus
        shareRequest.setShareStatus(shareStatus);

        // Update the ShareRequest in the database
        int rows = shareRequestMapper.updateById(shareRequest);

        // Return a success message if the update was successful, else return a 500 status
        if (rows > 0) {
            return ResponseEntity.ok("Share status updated successfully.");
        } else {
            return ResponseEntity.status(500).body("Failed to update share status.");
        }
    }

    // Delete a ShareRequest by its ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteShareRequest(@PathVariable Long id) {
        // Delete the ShareRequest by its ID
        int rows = shareRequestMapper.deleteById(id);

        // Return a success message if the deletion was successful, else return a 500 status
        if (rows > 0) {
            return ResponseEntity.ok("ShareRequest deleted successfully.");
        } else {
            return ResponseEntity.status(500).body("Failed to delete ShareRequest.");
        }
    }
}

