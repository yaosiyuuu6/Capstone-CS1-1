package com.example.Neighborhood_Walk.controller;

import com.example.Neighborhood_Walk.service.ShareLocationService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class ParentLocationWebSocketHandler extends TextWebSocketHandler {

    private final ShareLocationService shareLocationService;
    private final RedisTemplate<String, String> redisTemplate;  // Using String-String RedisTemplate

    // Constructor injection for dependencies
    public ParentLocationWebSocketHandler(ShareLocationService shareLocationService, RedisTemplate<String, String> redisTemplate) {
        this.shareLocationService = shareLocationService;
        this.redisTemplate = redisTemplate;
    }

    private static final Logger logger = LoggerFactory.getLogger(ParentLocationWebSocketHandler.class);

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // Get the payload (message content) from the WebSocket message
        String payload = message.getPayload();
        logger.info("Received WebSocket message from parent: {}", payload);

        // Assume the frontend sends JSON data containing agreementId, parentId, and walkerId
        JsonNode jsonNode = new ObjectMapper().readTree(payload);
        String parentId = jsonNode.get("parentId").asText();
        String agreementId = jsonNode.get("agreementId").asText();
        String walkerId = jsonNode.get("walkerId").asText();

        // Log the parent ID and related details for debugging
        logger.info("WebSocket connection established for parentId: {} agreementId: {} walkerId: {}", parentId, agreementId, walkerId);

        // Retrieve the latest location data of the walker from Redis
        String locationData = shareLocationService.getLatestLocationFromRedis(walkerId, agreementId);

        // If no data is found in Redis, try to fetch it from the database
        if (locationData == null || locationData.isEmpty()) {
            logger.warn("No location data found in Redis, checking the database for walkerId: {}, agreementId: {}", walkerId, agreementId);

            // Fetch location data from the database
            locationData = shareLocationService.getLatestLocationFromDatabase(walkerId, agreementId);

            if (locationData == null || locationData.isEmpty()) {
                logger.error("Failed to retrieve location data from both Redis and Database for walkerId: {}, agreementId: {}", walkerId, agreementId);
                session.sendMessage(new TextMessage("No location data available."));  // Send error message to the frontend
                return;  // Return early to avoid further execution if no data is found
            }
        }

        // If location data is available, send it to the parent
        if (locationData != null) {
            session.sendMessage(new TextMessage(locationData));
            logger.info("Sent location data to parentId: {}, for walkerId: {}, agreementId: {}", parentId, walkerId, agreementId);
        } else {
            session.sendMessage(new TextMessage("No location data available."));
            logger.info("No location data available for walkerId: {}, agreementId: {}", walkerId, agreementId);
        }
    }

}
