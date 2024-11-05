package com.example.Neighborhood_Walk.controller;

import com.example.Neighborhood_Walk.dto.LocationData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

@Component
public class LocationWebSocketHandler extends TextWebSocketHandler {

    private RedisTemplate<String, Object> redisTemplate;

    public LocationWebSocketHandler(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    private ObjectMapper objectMapper = new ObjectMapper();

    private static final Logger logger = LoggerFactory.getLogger(LocationWebSocketHandler.class);

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // Extract the payload from the received WebSocket message
        String payload = message.getPayload();
        logger.info("Received WebSocket message: {}", payload);

        // Deserialize the payload into a LocationData object
        LocationData locationData = objectMapper.readValue(payload, LocationData.class);

        // Check if redisTemplate is properly configured
        if (redisTemplate == null) {
            logger.error("redisTemplate is null - Redis is not configured properly.");
        } else {
            // Create a Redis key using walkerId and agreementId
            String key = "location:" + locationData.getWalkerId() + ":" + locationData.getAgreementId();

            // Use the current timestamp as the score in a Redis sorted set
            double score = System.currentTimeMillis();

            // Serialize the LocationData object into a JSON string
            String locationJson = objectMapper.writeValueAsString(locationData);

            // Store the location data in Redis using a ZSet (Sorted Set) with the timestamp as the score
            redisTemplate.opsForZSet().add(key, locationJson, score);
            logger.info("Location data stored in Redis Sorted Set for walkerId {}", locationData.getWalkerId());
        }

        // Send a response back to the client acknowledging the location update
        session.sendMessage(new TextMessage("Location updated successfully: " + locationData));
    }

}
