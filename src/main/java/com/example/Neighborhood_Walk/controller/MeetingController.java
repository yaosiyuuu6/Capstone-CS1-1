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

    // Create a new PreMeeting
    @PostMapping("/createPreMeeting")
    public String createPreMeeting(@RequestBody PreMeeting preMeeting, String applicationId) {
        // Set the applicationId (primary key)
        preMeeting.setApplicationId(applicationId);

        // Insert the new PreMeeting into the database
        MeetingMapper.insert(preMeeting);

        return "Create Successfully";
    }

    // Get an existing PreMeeting by ID
    @GetMapping("/{id}")
    public PreMeeting getPreMeetingById(@PathVariable String id) {
        // Retrieve the PreMeeting from the database by its ID
        PreMeeting preMeeting = MeetingMapper.findById(id);

        // If the PreMeeting exists, return it; otherwise, return null
        if (preMeeting != null) {
            return preMeeting;
        } else {
            return null; // Optionally, you could return a 404 response instead
        }
    }

    // Update an existing PreMeeting
    @PutMapping("/{id}/edit")
    public String updatePreMeeting(@PathVariable String id, @RequestBody PreMeeting updatedPreMeeting) {
        // Find the existing PreMeeting by its ID
        PreMeeting existingPreMeeting = MeetingMapper.findById(id);

        if (existingPreMeeting != null) {
            // Ensure the applicationId remains unchanged
            updatedPreMeeting.setApplicationId(existingPreMeeting.getApplicationId());

            // Update the existing PreMeeting with new data
            MeetingMapper.update(updatedPreMeeting);

            return "Update Successfully";
        } else {
            return "PreMeeting not found"; // Return an error message if PreMeeting not found
        }
    }

}
