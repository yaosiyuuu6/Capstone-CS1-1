package com.example.Neighborhood_Walk.controller;

import com.example.Neighborhood_Walk.Mapper.ProfileMapper;
import com.example.Neighborhood_Walk.entity.WalkerProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private ProfileMapper profileMapper;
    @PostMapping("/createProfile")
    public String createProfile(@RequestBody WalkerProfile profile, String id) {
        // set walker id(PK)
        profile.setWalkerId(id);
        profileMapper.insert(profile);
        return "Create Successfully";
    }

    // Method to get the existing request by ID
    @GetMapping("/{id}")
    public WalkerProfile getRById(@PathVariable String id) {
        WalkerProfile profile = profileMapper.findById(id);
        if (profile != null) {
            return profile;
        } else {
            return null;
        }
    }

    // Method to update an existing request
    @PutMapping("/{id}/edit")
    public String updateProfile(@PathVariable String id, @RequestBody WalkerProfile updatedProfile) {
        WalkerProfile existingProfile = profileMapper.findById(id);
        if (existingProfile != null) {
            updatedProfile.setWalkerId(existingProfile.getWalkerId());
            // Ensure the ID remains the same
            profileMapper.update(updatedProfile);
            return "Update Successfully";
        } else {
            return "Request not found";
        }
    }
}
