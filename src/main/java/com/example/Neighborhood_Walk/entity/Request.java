package com.example.Neighborhood_Walk.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalTime;
import java.util.Date;
import java.util.Map;


@TableName("WalkRequests")
public class Request {
    @TableId
    private String requestId;
    private String parentId;
    private String childId;
    private String pickupAddressId;
    private String dropoffAddressId;
    private Map<String, Object> scheduletime;
    private String walkDate;
    private String walkTime;
    private String recurrence;
    private String status;
    private String description;


    // Getters and Setters

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getChildName() {
        return childId;
    }

    public void setChildName(String childId) {
        this.childId = childId;
    }

    public String getPickupAddress() {
        return pickupAddressId;
    }

    public void setPickupAddress(String pickupAddressId) {
        this.pickupAddressId = pickupAddressId;
    }

    public String getDropoffAddress() {
        return dropoffAddressId;
    }

    public void setDropoffAddress(String dropoffAddressId) {
        this.dropoffAddressId = dropoffAddressId;
    }

    public String getWalkDate() {
        return walkDate;
    }

    public void setWalkDate(String walkDate) {
        this.walkDate = walkDate;
    }

    public String getWalkTime() {
        return walkTime;
    }

    public void setWalkTime(String walkTime) {
        this.walkTime = walkTime;
    }

    public String getRecurrence() {
        return recurrence;
    }

    public void setRecurrence(String recurrence) {
        this.recurrence = recurrence;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<String, Object> getScheduletime() {
        return scheduletime;
    }

    public void setScheduletime(Map<String, Object> scheduletime) {
        this.scheduletime = scheduletime;
    }

}

