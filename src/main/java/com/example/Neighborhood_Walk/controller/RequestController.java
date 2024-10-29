package com.example.Neighborhood_Walk.controller;


import com.example.Neighborhood_Walk.Mapper.RequestMapper;
import com.example.Neighborhood_Walk.dto.RequestDto;
import com.example.Neighborhood_Walk.entity.Address;
import com.example.Neighborhood_Walk.entity.Request;

import com.example.Neighborhood_Walk.service.AddressService;
import com.example.Neighborhood_Walk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/requests")
public class RequestController {

    @Autowired
    private RequestMapper requestMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private AddressService addressService;

    @PostMapping("/createRequest")
    public String createRequest(@RequestBody Request request) {
        // set request id(PK)
        request.setRequestId(UUID.randomUUID().toString());
        requestMapper.insert(request);
        return "Create Successfully";
    }
    @GetMapping("/list")
    public List<Request> getAllRequests(@RequestParam String parentId) {
        return requestMapper.findById(parentId);
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
        System.out.println(updatedRequest.getRequestId());
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

    @GetMapping("/nearby-requests")
    public ResponseEntity<List<RequestDto>> getNearbyRequests(
            @RequestParam String walkerId,
            @RequestParam double rangeInKm) {

        // 1. 获取 walker's 地址经纬度
        Address walkerAddress = userService.getUserAddressByUserId(walkerId);
        if (walkerAddress == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        // 2. 在地址表中根据距离筛选出符合要求的地址ID
        List<String> nearbyAddressIds = addressService.getNearbyAddressIds(
                walkerAddress.getLatitude(),
                walkerAddress.getLongitude(),
                rangeInKm
        );

        // 3. 在请求表中找到符合地址ID的请求
        List<RequestDto> nearbyRequests = requestMapper.findRequestsByAddressIds(nearbyAddressIds, walkerAddress.getLatitude().doubleValue(),
                walkerAddress.getLongitude().doubleValue());

        // 按距离从近到远排序
        nearbyRequests.sort(Comparator.comparingDouble(RequestDto::getDistance));

        return ResponseEntity.ok(nearbyRequests);
    }

    @DeleteMapping("/{id}/delete")
    public String deleteRequest(@PathVariable String id) {
        Request request = requestMapper.selectById(id);
        if (request != null) {
            requestMapper.deleteById(id);
            return "Delete Successfully";
        } else {
            return "Request not found";
        }
    }

    // 根据 requestId 获取 request 的详细信息
//    @GetMapping("/{requestId}")
//    public ResponseEntity<Request> getRequestById(@PathVariable String requestId) {
//        Request request = requestMapper.selectById(requestId);
//        if (request != null) {
//            return ResponseEntity.ok(request);
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        }
//    }

}
