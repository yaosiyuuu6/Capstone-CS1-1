package com.example.Neighborhood_Walk.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("Users")
public class User {
    @TableId
    private String userId;

    private String firstName;
    private String lastName;
    private String preferredName;
    private String password;
    private String email;
    private String phoneNumber;
    private String addressId;
    private String dateOfBirth;
    private String communicationPreference;
    private String userType;
    private String description;

    // Getters and Setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPreferredName() {
        return preferredName;
    }

    public void setPreferredName(String preferredName) {
        this.preferredName = preferredName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getCommunicationPreference() {
        return communicationPreference;
    }

    public void setCommunicationPreference(String communicationPreference) {
        this.communicationPreference = communicationPreference;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description ;
    }

    public User(String userId, String firstName, String lastName, String preferredName, String password, String email, String phoneNumber, String addressId, String dateOfBirth, String communicationPreference, String userType, String description) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.preferredName = preferredName;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.addressId = addressId;
        this.dateOfBirth = dateOfBirth;
        this.communicationPreference = communicationPreference;
        this.userType = userType;
        this.description = description;
    }

    public User() {

    }


}
