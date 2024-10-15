package com.example.Neighborhood_Walk.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("WalkerRating_View")
public class WalkerRating {
    private String walkerId;
    private String rating;
    private String ratingNum;
}
