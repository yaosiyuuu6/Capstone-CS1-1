package com.example.Neighborhood_Walk.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;

@Data
@TableName("WalkerApplications")
public class Application {

    @TableId(value = "application_id", type = IdType.INPUT)
    private String applicationId;

    private String walkerId;
    private String requestId;
    private String applicationStatus;
    private Date applicationDate;
    private Boolean preMeetScheduled;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String description;

    // getter and setter methods

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getWalkerId() {
        return walkerId;
    }

    public void setWalkerId(String walkerId) {
        this.walkerId = walkerId;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(String applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

    public Date getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(Date applicationDate) {
        this.applicationDate = applicationDate;
    }

    public Boolean getPreMeetScheduled() {
        return preMeetScheduled;
    }

    public void setPreMeetScheduled(Boolean preMeetScheduled) {
        this.preMeetScheduled = preMeetScheduled;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
