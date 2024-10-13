package com.example.Neighborhood_Walk.entity;



import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("AgreementDetails_View") // 替换为视图的名称
public class AgreementDetail {

    private String agreementId;
    private String walkerId;
    private String startTime;
    private String status;
    private String commentDate;
    private String trackingData;
    private String comments;
    private String ratingValue;
    private String walkDate;
    private String walkTime;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String pickupAddress;
    private String dropoffAddress;
    private String childName;
    private Integer childAge;
    private String childGender;

    // Getter and Setter methods (if not using Lombok)
}

