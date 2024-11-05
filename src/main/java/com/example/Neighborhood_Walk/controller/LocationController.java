package com.example.Neighborhood_Walk.controller;

import com.example.Neighborhood_Walk.Mapper.LocationMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.Neighborhood_Walk.entity.Location;


@RestController
@RequestMapping("/location")
public class LocationController {

    @Value("${google.api.key}")
    private String googleApiKey;

    private static final String GOOGLE_API_URL = "https://www.googleapis.com/geolocation/v1/geolocate?key=";

    // Get the full Google API URL by appending the API key
    public String getGoogleApiUrl() {
        return GOOGLE_API_URL + googleApiKey;
    }

    @Autowired
    private LocationMapper locationMapper;

    @GetMapping("/get")
    public ResponseEntity<String> getLocation(@RequestHeader(value = "User-Agent", required = false) String userAgent) {
        try {
            // If no User-Agent is provided, use a default value
            if (userAgent == null || userAgent.isEmpty()) {
                userAgent = "Default-User-Agent";
            }

            // Create a RestTemplate instance to send HTTP requests
            RestTemplate restTemplate = new RestTemplate();

            // Set up HTTP headers
            HttpHeaders headers = new HttpHeaders();
            headers.set("User-Agent", userAgent);  // Set the User-Agent header

            // Create the request entity with headers
            HttpEntity<String> entity = new HttpEntity<>(headers);

            // Send the HTTP request and get the response
            ResponseEntity<String> response = restTemplate.exchange(
                    getGoogleApiUrl(), HttpMethod.POST, entity, String.class);

            // Use ObjectMapper to parse the JSON response
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.getBody());

            // Extract latitude, longitude, and accuracy from the JSON response
            double latitude = root.path("location").path("lat").asDouble();
            double longitude = root.path("location").path("lng").asDouble();
            double accuracy = root.path("accuracy").asDouble();

            // Create a new Location object and populate it with the parsed data
            Location location = new Location();
            location.setLatitude(latitude);
            location.setLongitude(longitude);
            location.setAccuracy(accuracy);

            // Insert the location data into the database using the locationMapper
            locationMapper.insertLocation(location);

            // Return the original API response to the client
            return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();  // Print the error for debugging purposes
            return new ResponseEntity<>("Failed to get location", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}