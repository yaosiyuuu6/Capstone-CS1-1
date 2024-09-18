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

    // 获取完整的 Google API URL
    public String getGoogleApiUrl() {
        return GOOGLE_API_URL + googleApiKey;
    }
    @Autowired
    private LocationMapper locationMapper;

    @GetMapping("/get")
    public ResponseEntity<String> getLocation(@RequestHeader(value = "User-Agent", required = false) String userAgent) {
        try {
            // 如果没有提供 User-Agent，则使用默认值
            if (userAgent == null || userAgent.isEmpty()) {
                userAgent = "Default-User-Agent";
            }

            // 创建RestTemplate实例，用于发送HTTP请求
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.set("User-Agent", userAgent); // 设置请求头

            // 构造请求实体
            HttpEntity<String> entity = new HttpEntity<>(headers);

            // 发起请求并获取响应
            ResponseEntity<String> response = restTemplate.exchange(getGoogleApiUrl(), HttpMethod.POST, entity, String.class);

            // 使用ObjectMapper解析JSON响应
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.getBody());

            // 从JSON中提取纬度、经度和精度
            double latitude = root.path("location").path("lat").asDouble();
            double longitude = root.path("location").path("lng").asDouble();
            double accuracy = root.path("accuracy").asDouble();

            // 创建Location对象并保存到数据库
            Location location = new Location();
            location.setLatitude(latitude);
            location.setLongitude(longitude);
            location.setAccuracy(accuracy);
            locationMapper.insertLocation(location);  // 调用mapper插入数据

            return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();  // 打印异常信息到控制台，方便查看
            return new ResponseEntity<>("Failed to get location", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}