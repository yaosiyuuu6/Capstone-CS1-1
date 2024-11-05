package com.example.Neighborhood_Walk.entity;



import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("AgreementDetails_View")
public class AgreementDetail {

    private String agreementId;
    private String walkerId;
    private String startTime;
    private String status;
    private String commentDate;
    private String trackingData;
    private String comments;
    private String ratingValue;
    private String parentId;
    private String walkDate;
    private String walkTime;
    private String description;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String avatar;
    private String pickupAddress;
    private String dropoffAddress;
    private String childName;
    private Integer childAge;
    private String childGender;

    // Getter and Setter methods (if not using Lombok)
}

