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
        String payload = message.getPayload();
        logger.info("Received WebSocket message: {}", payload);

        LocationData locationData = objectMapper.readValue(payload, LocationData.class);

        if (redisTemplate == null) {
            logger.error("redisTemplate is null - Redis is not configured properly.");
        } else {

            // 使用有序集合（Sorted Set）将位置信息存储在 Redis 中，分数为时间戳
            String key = "location:" + locationData.getWalkerId() + ":" + locationData.getAgreementId();
            double score = System.currentTimeMillis(); // 当前时间戳作为分数

            // 将位置信息以 JSON 字符串存储
            String locationJson = objectMapper.writeValueAsString(locationData);

            // 使用 ZSet 存储数据
            redisTemplate.opsForZSet().add(key, locationJson, score);
            logger.info("Location data stored in Redis Sorted Set for walkerId {}", locationData.getWalkerId());
        }

        session.sendMessage(new TextMessage("位置更新成功: " + locationData));
    }
}
