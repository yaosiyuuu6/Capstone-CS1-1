package com.example.Neighborhood_Walk.service;

import com.example.Neighborhood_Walk.Mapper.ShareLocationMapper;
import com.example.Neighborhood_Walk.dto.LocationData;
import com.example.Neighborhood_Walk.entity.ShareLocation;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
public class ShareLocationService {

    @Autowired
    private ShareLocationMapper shareLocationMapper;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    // 创建 ObjectMapper 对象，用于序列化和反序列化
    private final ObjectMapper objectMapper = new ObjectMapper();

    // 日志记录器
    private static final Logger logger = LoggerFactory.getLogger(ShareLocationService.class);

    // 每 1 分钟执行一次定时任务
    @Scheduled(fixedRate = 60000)  // 每 1 分钟执行一次
    public void syncLocationsToDatabase() {
        Set<String> keys = redisTemplate.keys("location:*");

        if (keys != null && !keys.isEmpty()) {
            for (String key : keys) {
                try {
                    // 从 Redis 中读取位置信息的 JSON 字符串
                    Set<String> locationDataSet = redisTemplate.opsForZSet().range(key, 0, -1);

                    // 提取 key 中的 walkerId 和 agreementId
                    String[] keyParts = key.split(":");
                    String walkerId = keyParts[1];
                    String agreementId = keyParts[2];
                    // 遍历每个存储的 JSON 字符串
                    for (String locationDataJson : locationDataSet) {
                        // 直接将位置信息插入数据库
                        shareLocationMapper.insertLocationData(
                                agreementId,
                                walkerId,
                                locationDataJson
                        );
                        logger.info("Location data inserted into database for walkerId {} and agreementId {}",
                                walkerId, agreementId);
                    }

                    // 可选：清除 Redis 中已经同步到数据库的位置信息
                    redisTemplate.delete(key);

                } catch (Exception e) {
                    logger.error("Error syncing location data to database", e);
                }
            }
        }
    }

    // 保存位置信息到 Redis
    public void saveLocation(ShareLocation shareLocation) {
        // 使用 agreementId 作为 Redis 中的 key
        String key = "location:" + shareLocation.getAgreementId();

        try {
            // 将位置信息存入 Redis
            redisTemplate.opsForValue().set(key, objectMapper.writeValueAsString(shareLocation));
            logger.info("Location data for agreementId {} saved to Redis.", shareLocation.getAgreementId());
        } catch (Exception e) {
            logger.error("Error saving location data to Redis for agreementId {}", shareLocation.getAgreementId(), e);
        }
    }

    // 更新 MySQL 中的位置信息
    public void updateLocationTrackingData(String agreementId, String trackingData) {
        try {
            shareLocationMapper.updateTrackingData(agreementId, trackingData);
            logger.info("Tracking data for agreementId {} updated in database.", agreementId);
        } catch (Exception e) {
            logger.error("Error updating tracking data for agreementId {} in database.", agreementId, e);
        }
    }

    // 通过 agreementId 获取位置信息
    public ShareLocation getLocationByAgreementId(String agreementId) {
        return shareLocationMapper.selectById(agreementId);
    }

    // 从 Redis 中更新位置信息到 MySQL
    public void updateLocationsFromRedis(List<String> agreementIds, List<String> trackingDataList) {
        for (int i = 0; i < agreementIds.size(); i++) {
            updateLocationTrackingData(agreementIds.get(i), trackingDataList.get(i));
        }
    }
}
