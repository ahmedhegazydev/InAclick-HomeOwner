package com.example.hp.in_a_click.model;

/**
 * Created by hp on 11/15/2017.
 */

public class UserNormal {
    String userEmail = "";
    String userPass = "";
    String userName = "";
    String userPhone = "";
    String city = "";
    String driverId = "";
    String userType = "";//user normal
    String userPhotoUrl = "";

    public UserNormal(String userEmail, String userPass, String userName, String userPhone, String city, String driverId, String userType, String userPhotoUrl) {
        this.userEmail = userEmail;
        this.userPass = userPass;
        this.userName = userName;
        this.userPhone = userPhone;
        this.city = city;
        this.driverId = driverId;
        this.userType = userType;
        this.userPhotoUrl = userPhotoUrl;
    }

    public UserNormal(String userEmail, String userPass, String userName, String userPhone) {
        this.userEmail = userEmail;
        this.userPass = userPass;
        this.userName = userName;
        this.userPhone = userPhone;
    }

    public UserNormal() {
    }

    public UserNormal(String userEmail, String userPass, String userName, String userPhone, String city, String driverId, String userType) {
        this.userEmail = userEmail;
        this.userPass = userPass;
        this.userName = userName;
        this.userPhone = userPhone;
        this.city = city;
        this.driverId = driverId;
        this.userType = userType;
    }

    public UserNormal(String userEmail, String userPass, String userName, String userPhone, String city) {
        this.userEmail = userEmail;
        this.userPass = userPass;
        this.userName = userName;
        this.userPhone = userPhone;
        this.city = city;
    }

    public String getDriverId() {
        return driverId;
    }

    public String getUserType() {
        return userType;
    }

    public String getUserPhotoUrl() {
        return userPhotoUrl;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }
}
