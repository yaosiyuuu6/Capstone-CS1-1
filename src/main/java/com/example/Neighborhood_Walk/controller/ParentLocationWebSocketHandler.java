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
    private final RedisTemplate<String, String> redisTemplate;  // 使用 String-String RedisTemplate

    // 构造函数注入
    public ParentLocationWebSocketHandler(ShareLocationService shareLocationService, RedisTemplate<String, String> redisTemplate) {
        this.shareLocationService = shareLocationService;
        this.redisTemplate = redisTemplate;
    }

    private static final Logger logger = LoggerFactory.getLogger(ParentLocationWebSocketHandler.class);

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        logger.info("Received WebSocket message from parent: {}", payload);

        // 假设前端传递的 JSON 数据包含 agreementId 和 walkerId
        JsonNode jsonNode = new ObjectMapper().readTree(payload);
        String parentId = jsonNode.get("parentId").asText();
        String agreementId = jsonNode.get("agreementId").asText();
        String walkerId = jsonNode.get("walkerId").asText();

        // 打印 Parent ID
        logger.info("WebSocket connection established for parentId: {} agreementId: {} walker id: {}", parentId, agreementId, walkerId);

        // 从 Redis 获取 walker 最新位置信息
        String locationData = shareLocationService.getLatestLocationFromRedis(walkerId, agreementId);

        // 如果 Redis 中没有数据，则从数据库中获取
        if (locationData == null || locationData.isEmpty()) {
            logger.warn("No location data found in Redis, checking the database for walkerId: {}, agreementId: {}", walkerId, agreementId);

            // 从数据库获取位置数据
            locationData = shareLocationService.getLatestLocationFromDatabase(walkerId, agreementId);

            if (locationData == null || locationData.isEmpty()) {
                logger.error("Failed to retrieve location data from both Redis and Database for walkerId: {}, agreementId: {}", walkerId, agreementId);
                session.sendMessage(new TextMessage("No location data available.")); // 发送错误消息到前端
                return; // 如果没有找到数据，直接返回，避免后续 NullPointerException
            }
        }

        // 将位置信息发送给 Parent
        if (locationData != null) {
            session.sendMessage(new TextMessage(locationData));
            logger.info("Sent location data to parentId: {}, for walkerId: {}, agreementId: {}", parentId, walkerId, agreementId);
        } else {
            session.sendMessage(new TextMessage("No location data available."));
            logger.info("No location data available for walkerId: {}, agreementId: {}", walkerId, agreementId);
        }
    }
}
