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

    // Create a new Request
    @PostMapping("/createRequest")
    public String createRequest(@RequestBody Request request) {
        // Set a unique requestId (Primary Key)
        request.setRequestId(UUID.randomUUID().toString());

        // Insert the new request into the database
        requestMapper.insert(request);

        return "Create Successfully";
    }

    // Get all requests for a specific parent by parentId
    @GetMapping("/list")
    public List<Request> getAllRequests(@RequestParam String parentId) {
        return requestMapper.findById(parentId);
    }

    // Get a specific request by its ID
    @GetMapping("/{id}")
    public Request getRequestById(@PathVariable String id) {
        Request request = requestMapper.selectById(id);

        // Return the request if found, otherwise return null
        if (request != null) {
            return request;
        } else {
            return null;  // Optionally, return a 404 status if preferred
        }
    }

    // Get all requests (without filtering)
    @GetMapping("/all")
    public List<Request> getAllRequest() {
        return requestMapper.getAllRequest();
    }

    // Update an existing request by its ID
    @PutMapping("/{id}/edit")
    public String updateRequest(@PathVariable String id, @RequestBody Request updatedRequest) {
        System.out.println(updatedRequest.getRequestId());

        // Find the existing request by its ID
        Request existingRequest = requestMapper.selectById(id);

        if (existingRequest != null) {
            // Ensure parentId and requestId are not changed
            updatedRequest.setParentId(existingRequest.getParentId());
            updatedRequest.setRequestId(existingRequest.getRequestId());

            // Update the request in the database
            requestMapper.update(updatedRequest);
            return "Update Successfully";
        } else {
            return "Request not found";
        }
    }

    // Get nearby requests based on the walker's location
    @GetMapping("/nearby-requests")
    public ResponseEntity<List<RequestDto>> getNearbyRequests(
            @RequestParam String walkerId,
            @RequestParam double rangeInKm) {

        // 1. Get the walker's address (latitude, longitude) by their userId
        Address walkerAddress = userService.getUserAddressByUserId(walkerId);
        if (walkerAddress == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        // 2. Find nearby address IDs within the specified range
        List<String> nearbyAddressIds = addressService.getNearbyAddressIds(
                walkerAddress.getLatitude(),
                walkerAddress.getLongitude(),
                rangeInKm
        );

        // 3. Find requests that match the nearby address IDs
        List<RequestDto> nearbyRequests = requestMapper.findRequestsByAddressIds(
                nearbyAddressIds,
                walkerAddress.getLatitude().doubleValue(),
                walkerAddress.getLongitude().doubleValue()
        );

        // Sort the requests by distance (closest first)
        nearbyRequests.sort(Comparator.comparingDouble(RequestDto::getDistance));

        // Return the sorted list of nearby requests
        return ResponseEntity.ok(nearbyRequests);
    }

    // Delete a request by its ID
    @DeleteMapping("/{id}/delete")
    public String deleteRequest(@PathVariable String id) {
        Request request = requestMapper.selectById(id);

        if (request != null) {
            // Delete the request from the database
            requestMapper.deleteById(id);
            return "Delete Successfully";
        } else {
            return "Request not found";
        }
    }


}
