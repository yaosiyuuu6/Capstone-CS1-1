package com.example.Neighborhood_Walk.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

@TableName("WalkerProfile")
public class WalkerProfile {

    @TableId
    private String walkerId;

    // 用于存储是否具有和儿童一起工作的许可
    // Working with Children Check
    private String workingWithChildrenCheck;

    // Schedule type: Weekly or One-off
    private String scheduleType;

    private String availableDatesTimes;

    // skill
    private String skills;

    // One-off date
    private Date availableDate;

    // TimePeriod
    public enum TimePeriod {
        Morning, Afternoon, Evening, Night;
    }

    private String timePeriod;


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

    public String getScheduleType() {
        return scheduleType;
    }

    public void setScheduleType(String scheduleType) {
        this.scheduleType = scheduleType;
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

    public Date getAvailableDate() {
        return availableDate;
    }

    public void setAvailableDate(Date availableDate) {
        this.availableDate = availableDate;
    }

    public String getTimePeriod() {
        return timePeriod;
    }

    public void setTimePeriod(String timePeriod) {
        this.timePeriod = timePeriod;
    }
}
