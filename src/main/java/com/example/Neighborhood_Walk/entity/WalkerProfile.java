package com.example.Neighborhood_Walk.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

@TableName("WalkerProfile")
public class WalkerProfile {

    @TableId
    private String walkerId;

    // 用于存储是否具有和儿童一起工作的许可
    private String workingWithChildrenCheck;

    // Schedule type: Weekly or One-off
    private ScheduleType scheduleType;  // 用于区分使用 availableDatesTimes 还是 availableDate + timePeriod

    // JSON 字段，存储详细的可用时间段
    private String availableDatesTimes;

    // 技能描述
    private String skills;

    // One-off 类型时使用的日期
    private Date availableDate;

    // TimePeriod 枚举类型，用于表示 Morning, Afternoon, Evening, Night
    public enum TimePeriod {
        Morning, Afternoon, Evening, Night;
    }

    private TimePeriod timePeriod;  // One-off 类型时使用的时间段

    // ScheduleType 枚举，用于表示是 Weekly 还是 One-off
    public enum ScheduleType {
        Weekly, one_off;
    }

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

    public ScheduleType getScheduleType() {
        return scheduleType;
    }

    public void setScheduleType(ScheduleType scheduleType) {
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

    public TimePeriod getTimePeriod() {
        return timePeriod;
    }

    public void setTimePeriod(TimePeriod timePeriod) {
        this.timePeriod = timePeriod;
    }
}
