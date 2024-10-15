package com.example.Neighborhood_Walk.dto;

import java.util.Date;
import java.util.Map;
import com.example.Neighborhood_Walk.entity.WalkerProfile.TimePeriod;

public class WalkerProfileDTO {

    private String walkerId;

    private Map<String, Object> availableDatesTimes;  // 用于 Weekly 类型

    private String skills;

    private Date availableDate;  // 用于 One-off 类型

    private TimePeriod timePeriod;  // 用于 One-off 类型

    // Getters and Setters
    public String getWalkerId() {
        return walkerId;
    }

    public void setWalkerId(String walkerId) {
        this.walkerId = walkerId;
    }

    public Map<String, Object> getAvailableDatesTimes() {
        return availableDatesTimes;
    }

    public void setAvailableDatesTimes(Map<String, Object> availableDatesTimes) {
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
