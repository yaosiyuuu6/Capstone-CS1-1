package com.example.Neighborhood_Walk.controller;


import com.example.Neighborhood_Walk.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class SmsController {
    @Autowired
    private SmsService smsService;

    // Send an SMS message
    @PostMapping("/send")
    public ResponseEntity<String> sendSms(@RequestParam String phoneNumber, @RequestParam String message) {
        // Call the SMS service to send the message
        String result = smsService.sendSms(phoneNumber, message);

        // Return the result of the SMS sending operation
        return ResponseEntity.ok(result);
    }

}


