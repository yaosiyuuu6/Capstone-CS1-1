package com.example.Neighborhood_Walk.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("WalkerProfile")
public class WalkerProfile {
    @TableId
    private String walkerId;
    private String workingWithChildrenCheck;
    private String availableDatesTimes;
    private String skills;

    // Getters and Setters
    public String getWalkerId() {
        return walkerId;
    }

    public void setWalkerId(String walkerId) {
        this.walkerId = walkerId;
    }

    public String getWorkingWithChildrenCheck() {
        return workingWithChildrenCheck;
    }

    public void setWorkingWithChildrenCheck(String workingWithChildrenCheck) {
        this.workingWithChildrenCheck = workingWithChildrenCheck;
    }

    public String getAvailableDatesTimes() {
        return availableDatesTimes;
    }

    public void setAvailableDatesTimes(String availableDatesTimes) {
        this.availableDatesTimes = availableDatesTimes;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }
}

