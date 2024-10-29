package com.example.Neighborhood_Walk.entity;

import com.baomidou.mybatisplus.annotation.TableField;
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

    @TableField("child_id")
    private String childId;
    private String pickupAddressId;
    private String dropoffAddressId;
    private String scheduletime;
    private String walkDate;
    private String walkTime;
    private String recurrence;
    private String status;
    private String description;

    private String createdAt;

    private String updatedAt;

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

    public String getChildId() {
        return childId;
    }

    public void setChildId(String childId) {
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

    public String getScheduletime() {
        return scheduletime;
    }

    public void setScheduletime(String scheduletime) {
        this.scheduletime = scheduletime;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

}

