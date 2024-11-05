package com.example.Neighborhood_Walk.controller;

import com.example.Neighborhood_Walk.entity.ShareLocation;
import com.example.Neighborhood_Walk.service.ShareLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/sharelocation")
public class ShareLocationController {

    @Autowired
    private ShareLocationService shareLocationService;

    // Start location sharing
    @PostMapping("/start/{agreementId}")
    public String startLocationSharing(@PathVariable String agreementId, @RequestBody ShareLocation shareLocation) {
        // Set the agreementId to distinguish between different sharing sessions
        shareLocation.setAgreementId(agreementId);

        // Save the location data to Redis or another storage medium
        shareLocationService.saveLocation(shareLocation);

        return "Location sharing started.";
    }

    // Stop location sharing
    @PostMapping("/stop/{agreementId}")
    public String stopLocationSharing(@PathVariable String agreementId) {
        // Retrieve the current location sharing session by agreementId
        ShareLocation shareLocation = shareLocationService.getLocationByAgreementId(agreementId);

        // Set the end time of the location sharing
        shareLocation.setEndTime(LocalDateTime.now());

        // Save the updated location data back to the storage
        shareLocationService.saveLocation(shareLocation);

        return "Location sharing stopped.";
    }

    // Get the current location of the Walker
    @GetMapping("/current/{agreementId}")
    public ShareLocation getCurrentLocation(@PathVariable String agreementId) {
        // Retrieve the latest location information for the given agreementId
        return shareLocationService.getLocationByAgreementId(agreementId);
    }

}

