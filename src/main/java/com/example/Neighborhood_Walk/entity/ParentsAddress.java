package com.example.Neighborhood_Walk.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("ParentsAddress")
public class ParentsAddress {

    @TableId
    private String userId;  // UUID格式的用户ID

    private String addressId;  // UUID格式的地址ID

    // Getters and Setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }
}
