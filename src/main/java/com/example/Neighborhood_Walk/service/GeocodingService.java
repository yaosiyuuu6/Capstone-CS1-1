package com.example.Neighborhood_Walk.service;

import com.example.Neighborhood_Walk.dto.Coordinates;
import org.json.JSONException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.json.JSONObject;
import java.math.BigDecimal;

@Service
public class GeocodingService {

    @Value("${google.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    public GeocodingService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // 将地址转换为经纬度


    public Coordinates reverseGeocode(String address) {
        try {
            String url = String.format(
                    "https://maps.googleapis.com/maps/api/geocode/json?address=%s&key=%s",
                    address.replace(" ", "+"), apiKey
            );

            String response = restTemplate.getForObject(url, String.class);
            JSONObject jsonObject = new JSONObject(response);

            if (!jsonObject.getString("status").equals("OK")) {
                throw new RuntimeException("无法获取坐标: " + jsonObject.getString("status"));
            }

            JSONObject location = jsonObject.getJSONArray("results")
                    .getJSONObject(0)
                    .getJSONObject("geometry")
                    .getJSONObject("location");

            // 使用 getDouble() 获取值，然后转换为 BigDecimal
            BigDecimal latitude = BigDecimal.valueOf(location.getDouble("lat"));
            BigDecimal longitude = BigDecimal.valueOf(location.getDouble("lng"));

            return new Coordinates(latitude, longitude);

        } catch (JSONException e) {
            throw new RuntimeException("解析JSON失败", e);
        }
    }
}


