package com.example.hp.in_a_click.model;

/**
 * Created by hp on 11/15/2017.
 */

public class UserDriver {


    private String userEmail;
    private String userPass;
    private String userName;
    private String userPhone;
    private String city = "";
    String carModelNumber = "";
    String carCatName = "";
    boolean driverStatus = false;//by default
    String userType = "";//user normal

    String passengerId = "";
    String destination = "";
    String destLat = "";
    String destLon= "";
    String service = "";//uber-x or uber-XL or .....
    String photoUrl= "";
    String driverRating = "";
    String numOfStarts = "";


    public UserDriver(String userEmail, String userPass, String userName,
                      String userPhone, String city, boolean driverStatus, String userType) {
        this.userEmail = userEmail;
        this.userPass = userPass;
        this.userName = userName;
        this.userPhone = userPhone;
        this.city = city;
        this.driverStatus = driverStatus;
        this.userType = userType;
    }

    public UserDriver(String userEmail, String userPass, String userName, String userPhone,
                      String city, String carModelNumber, String carCatName, boolean driverStatus) {
        this.userEmail = userEmail;
        this.userPass = userPass;
        this.userName = userName;
        this.userPhone = userPhone;
        this.city = city;
        this.carModelNumber = carModelNumber;
        this.carCatName = carCatName;
        this.driverStatus = driverStatus;
    }

    public UserDriver() {
    }

    public UserDriver(String userEmail, String userPass, String userName, String userPhone, String city, boolean driverStatus) {
        this.userEmail = userEmail;
        this.userPass = userPass;
        this.userName = userName;
        this.userPhone = userPhone;
        this.city = city;
        this.driverStatus = driverStatus;
    }

    public String getCarModelNumber() {
        return carModelNumber;
    }

    public void setCarModelNumber(String carModelNumber) {
        this.carModelNumber = carModelNumber;
    }

    public String getCarCatName() {
        return carCatName;
    }

    public void setCarCatName(String carCatName) {
        this.carCatName = carCatName;
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

    public boolean isDriverStatus() {

        return driverStatus;
    }

    public void setDriverStatus(boolean driverStatus) {
        this.driverStatus = driverStatus;
    }
}
