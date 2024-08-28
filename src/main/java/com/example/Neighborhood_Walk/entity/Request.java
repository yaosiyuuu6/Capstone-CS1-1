package com.example.Neighborhood_Walk.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalTime;
import java.util.Date;


@TableName("WalkRequests")
public class Request {
    @TableId
    private String requestId;
    private String parentId;
    private String childName;
    private String pickupAddressId;
    private String dropoffAddressId;
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
        return childName;
    }

    public void setChildName(String childName) {
        this.childName = childName;
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


    public Request(String requestId, String parentId, String childName, String pickupAddressId, String dropoffAddressId,
                   String walkDate, String walkTime, String recurrence, String status, String description) {
        this.requestId = requestId;
        this.parentId = parentId;
        this.childName = childName;
        this.pickupAddressId = pickupAddressId;
        this.dropoffAddressId = dropoffAddressId;
        this.walkDate = walkDate;
        this.walkTime = walkTime;
        this.recurrence = recurrence;
        this.status = status;
        this.description = description;
    }

}

