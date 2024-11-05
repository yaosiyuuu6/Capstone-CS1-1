package com.example.Neighborhood_Walk.controller;

import com.example.Neighborhood_Walk.entity.Application;
import com.example.Neighborhood_Walk.Mapper.ApplicationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/applications")
public class ApplicationController {

    @Autowired
    private ApplicationMapper applicationMapper;

    // Create a new application
    @PostMapping("/create")
    public String createApplication(@RequestBody Application application) {
        // Generate a new UUID for the applicationId
        application.setApplicationId(UUID.randomUUID().toString());

        // Set the current date for the application
        application.setApplicationDate(new Date());

        // Insert the new application into the database
        applicationMapper.insert(application);

        return "Application created successfully!";
    }

    // Get a specific application by its ID
    @GetMapping("/{id}")
    public Application getApplicationById(@PathVariable("id") String applicationId) {
        return applicationMapper.selectById(applicationId);
    }

    // Get applications by walker ID
    @GetMapping("/byWalker/{walker_id}")
    public List<Application> getApplicationByWalker(@PathVariable("walker_id") String walkerId) {
        System.out.println(walkerId);
        System.out.println(applicationMapper.findByWalker(walkerId));
        return applicationMapper.findByWalker(walkerId);
    }

    // Get applications by request ID
    @GetMapping("/byRequest/{request_id}")
    public List<Application> getApplicationByRequest(@PathVariable("request_id") String requestId) {
        return applicationMapper.findByRequest(requestId);
    }

    // Get all applications
    @GetMapping("/all")
    public List<Application> getAllApplications() {
        return applicationMapper.selectList(null); // Fetches all applications from the database
    }

    // Update an existing application
    @PutMapping("/{id}")
    public String updateApplication(@PathVariable("id") String applicationId, @RequestBody Application application) {
        // Find the existing application by its ID
        Application existingApplication = applicationMapper.selectById(applicationId);

        if (existingApplication == null) {
            return "Application not found."; // Return an error if the application doesn't exist
        }

        // Set the application ID to the existing one
        application.setApplicationId(applicationId);

        // Update the application in the database
        applicationMapper.updateById(application);

        return "Application updated successfully!";
    }

    // Delete an application by its ID
    @DeleteMapping("/{id}")
    public String deleteApplication(@PathVariable("id") String applicationId) {
        // Delete the application and check the result
        int result = applicationMapper.deleteById(applicationId);

        if (result > 0) {
            return "Application deleted successfully!";
        } else {
            return "Application not found.";
        }
    }


}
