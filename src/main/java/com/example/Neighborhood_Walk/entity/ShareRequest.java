package com.example.Neighborhood_Walk.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("ShareRequest")  // 对应数据库中的表名
public class ShareRequest {

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    @TableField("requestId")  // 明确指定数据库中的字段名
    private String requestId;

    @TableField("parentId")  // 明确指定数据库中的字段名
    private String parentId;

    @TableField("walkerId")  // 明确指定数据库中的字段名
    private String walkerId;

    @TableField("shareStatus")  // 明确指定数据库中的字段名
    private String shareStatus;

    @TableField("createdAt")
    private LocalDateTime createdAt;

    @TableField("updatedAt")
    private LocalDateTime updatedAt;

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getWalkerId() {
        return walkerId;
    }

    public void setWalkerId(String walkerId) {
        this.walkerId = walkerId;
    }

    public String getShareStatus() {
        return shareStatus;
    }

    public void setShareStatus(String shareStatus) {
        this.shareStatus = shareStatus;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }


}
