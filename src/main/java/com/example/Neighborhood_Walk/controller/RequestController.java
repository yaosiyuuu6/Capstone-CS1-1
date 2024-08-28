package com.example.Neighborhood_Walk.controller;

import com.example.Neighborhood_Walk.Mapper.AddressMapper;
import com.example.Neighborhood_Walk.Mapper.RequestMapper;
import com.example.Neighborhood_Walk.Mapper.UserMapper;
import com.example.Neighborhood_Walk.entity.Address;
import com.example.Neighborhood_Walk.entity.Request;
import com.example.Neighborhood_Walk.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/requests")
public class RequestController {

    @Autowired
    private RequestMapper requestMapper;
    @PostMapping("/createRequest")
    public String createRequest(@RequestBody Request request) {
        // set request id(PK)
        request.setRequestId(UUID.randomUUID().toString());
        requestMapper.insert(request);
        return "Create Successfully";
    }
    @GetMapping("/list")
    public List<Request> getAllRequests(String parent_id) {
        return requestMapper.findById(parent_id);
    }

    // Method to get the existing request by ID
    @GetMapping("/{id}")
    public Request getRequestById(@PathVariable String id) {
        Request request = requestMapper.selectById(id);
        if (request != null) {
            return request;
        } else {
            return null;
        }
    }

    // Method to update an existing request
    @PutMapping("/{id}/edit")
    public String updateRequest(@PathVariable String id, @RequestBody Request updatedRequest) {
        Request existingRequest = requestMapper.selectById(id);
        if (existingRequest != null) {
            updatedRequest.setParentId(existingRequest.getParentId());
            updatedRequest.setRequestId(existingRequest.getRequestId());
            //updatedRequest.setRequestId(id); // Ensure the ID remains the same
            requestMapper.update(updatedRequest);
            return "Update Successfully";
        } else {
            return "Request not found";
        }
    }

}
