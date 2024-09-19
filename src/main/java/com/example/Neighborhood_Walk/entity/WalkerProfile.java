package com.example.Neighborhood_Walk.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

@TableName("WalkerProfile")
public class WalkerProfile {

    @TableId
    private String walkerId;

    private String workingWithChildrenCheck;

    // 将 JSON 字段改为 String 类型以便 MyBatis 直接处理
    private String availableDatesTimes;

    private String skills;

    // available_date and time_period fields
    private Date availableDate;

    // 枚举类型，用于表示 Morning, Afternoon, Evening, Night
    public enum TimePeriod {
        MORNING, AFTERNOON, EVENING, NIGHT;
    }

    private TimePeriod timePeriod;

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

    public Date getAvailableDate() {
        return availableDate;
    }

    public void setAvailableDate(Date availableDate) {
        this.availableDate = availableDate;
    }

    public TimePeriod getTimePeriod() {
        return timePeriod;
    }

    public void setTimePeriod(TimePeriod timePeriod) {
        this.timePeriod = timePeriod;
    }
}
