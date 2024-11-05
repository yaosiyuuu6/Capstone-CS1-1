package com.example.Neighborhood_Walk.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class SmsService {
    private final RestTemplate restTemplate = new RestTemplate();
    private final String url = "https://cellcast.com.au/api/v3/send-sms";

    @Value("${cellcast.api.key}")
    private String apiKey;

    public String sendSms(String phoneNumber, String message) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("APPKEY", apiKey);

        Map<String, String> body = new HashMap<>();
        body.put("numbers", phoneNumber);
        body.put("sms_text", message);

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);

        return response.getBody();
    }

}
