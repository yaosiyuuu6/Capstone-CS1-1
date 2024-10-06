package com.example.Neighborhood_Walk.entity;

import java.time.LocalDateTime;

public class ShareLocation {

    private Long recordId;          // 记录 ID 自增 主键
    private String agreementId;   // UUID 合约 ID
    private String walkerId;        // Walker 用户 ID
    private String trackingData;  // JSON 格式的位置信息
    private LocalDateTime startTime;   // 位置共享的开始时间
    private LocalDateTime endTime;     // 位置共享的结束时间
    private LocalDateTime updatedAt;   // 最后更新时间

    // Getters 和 Setters
    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    // Getters 和 Setters
    public String getAgreementId() {
        return agreementId;
    }

    public void setAgreementId(String agreementId) {
        this.agreementId = agreementId;
    }

    public String getWalkerId() {
        return walkerId;
    }

    public void setWalkerId(String walkerId) {
        this.walkerId = walkerId;
    }

    public String getTrackingData() {
        return trackingData;
    }

    public void setTrackingData(String trackingData) {
        this.trackingData = trackingData;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
