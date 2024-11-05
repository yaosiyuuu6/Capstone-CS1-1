package com.example.Neighborhood_Walk.service;

import com.example.Neighborhood_Walk.Mapper.ShareLocationMapper;
import com.example.Neighborhood_Walk.dto.LocationData;
import com.example.Neighborhood_Walk.entity.ShareLocation;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

//    @Autowired
//    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    @Qualifier("stringRedisTemplate")  // 注入配置中的 stringRedisTemplate // Inject stringRedisTemplate in the configuration
    private RedisTemplate<String, String> stringRedisTemplate;


    // 创建 ObjectMapper 对象，用于序列化和反序列化
    // Create an ObjectMapper object for serialization and deserialization
    private final ObjectMapper objectMapper = new ObjectMapper();

    // 日志记录器
    // Logger
    private static final Logger logger = LoggerFactory.getLogger(ShareLocationService.class);

    // 每 1 分钟执行一次定时任务
    // Execute the scheduled task every 1 minute
    @Scheduled(fixedRate = 60000)
    public void syncLocationsToDatabase() {
        Set<String> keys = stringRedisTemplate.keys("location:*");

        if (keys != null && !keys.isEmpty()) {
            for (String key : keys) {
                try {
                    // 从 Redis 中读取位置信息的 JSON 字符串
                    // Read the JSON string of location information from Redis
                    Set<String> locationDataSet = stringRedisTemplate.opsForZSet().range(key, 0, -1);

                    // 提取 key 中的 walkerId 和 agreementId
                    // Extract walkerId and agreementId from key
                    String[] keyParts = key.split(":");
                    String walkerId = keyParts[1];
                    String agreementId = keyParts[2];
                    // 遍历每个存储的 JSON 字符串
                    // Traverse each stored JSON string
                    for (String locationDataJson : locationDataSet) {
                        // 直接将位置信息插入数据库
                        // Insert location information directly into the database
                        shareLocationMapper.insertLocationData(
                                agreementId,
                                walkerId,
                                locationDataJson
                        );
                        logger.info("Location data inserted into database for walkerId {} and agreementId {}",
                                walkerId, agreementId);
                    }

                    // 可选：清除 Redis 中已经同步到数据库的位置信息
                    // Optional: Clear the location information that has been synchronized to the database in Redis
                    stringRedisTemplate.delete(key);

                } catch (Exception e) {
                    logger.error("Error syncing location data to database", e);
                }
            }
        }
    }

    // 保存位置信息到 Redis
    // Save location information to Redis
    public void saveLocation(ShareLocation shareLocation) {
        // 使用 agreementId 作为 Redis 中的 key
        String key = "location:" + shareLocation.getAgreementId();

        try {
            // 将位置信息存入 Redis
            // Save location information to Redis
            stringRedisTemplate.opsForValue().set(key, objectMapper.writeValueAsString(shareLocation));
            logger.info("Location data for agreementId {} saved to Redis.", shareLocation.getAgreementId());
        } catch (Exception e) {
            logger.error("Error saving location data to Redis for agreementId {}", shareLocation.getAgreementId(), e);
        }
    }

    // 更新 MySQL 中的位置信息
    // Update the location information in MySQL
    public void updateLocationTrackingData(String agreementId, String trackingData) {
        try {
            shareLocationMapper.updateTrackingData(agreementId, trackingData);
            logger.info("Tracking data for agreementId {} updated in database.", agreementId);
        } catch (Exception e) {
            logger.error("Error updating tracking data for agreementId {} in database.", agreementId, e);
        }
    }

    // 通过 agreementId 获取位置信息
    // Get location information by agreementId
    public ShareLocation getLocationByAgreementId(String agreementId) {
        return shareLocationMapper.selectById(agreementId);
    }

    // 从 Redis 中更新位置信息到 MySQL
    // Update location information from Redis to MySQL
    public void updateLocationsFromRedis(List<String> agreementIds, List<String> trackingDataList) {
        for (int i = 0; i < agreementIds.size(); i++) {
            updateLocationTrackingData(agreementIds.get(i), trackingDataList.get(i));
        }
    }

    // 从 Redis 中获取最新的 walker 位置信息
    // Get the latest walker location information from Redis
    public String getLatestLocationFromRedis(String walkerId, String agreementId) {
        logger.info("Attempting to retrieve latest location from Redis for walkerId: {}, agreementId: {}", walkerId, agreementId);
        String key = "location:" + walkerId + ":" + agreementId;
        Set<String> locationDataSet = stringRedisTemplate.opsForZSet().range(key, -1, -1); // 获取最新的一条记录
        if (locationDataSet != null && !locationDataSet.isEmpty()) {
            return locationDataSet.iterator().next(); // 获取最新位置的 JSON 字符串
        }
        return null;
    }

    // 从数据库中获取最新的 walker 位置信息
    // Get the latest walker location information from the database
    public String getLatestLocationFromDatabase(String walkerId, String agreementId) {
        ShareLocation latestLocation = shareLocationMapper.getLatestLocation(walkerId, agreementId);
        if (latestLocation != null) {
            return latestLocation.getTrackingData(); // 获取 trackingData
        }
        return null;
    }

}
