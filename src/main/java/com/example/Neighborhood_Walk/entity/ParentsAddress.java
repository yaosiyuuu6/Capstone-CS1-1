package com.example.Neighborhood_Walk.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("ParentsAddress")
public class ParentsAddress {

    @TableId(value = "user_id") // 指定数据库字段名
    private String userId; // UUID格式的用户ID

    @TableField(value = "address_id") // 指定数据库字段名
    private String addressId; // UUID格式的地址ID

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
