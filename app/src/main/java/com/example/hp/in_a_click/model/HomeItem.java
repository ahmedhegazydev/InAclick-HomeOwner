package com.example.hp.in_a_click.model;

import java.io.Serializable;

/**
 * Created by ahmed on 28/12/17.
 */

public class HomeItem implements Serializable{

    String lat = "", lon = "", catName = "", locationMame = "", insertDate = "",
            salaryFromTo = "";
    int salaryOrRent = 0;//0 salary and 1 for rent
    boolean enabled = false;//by default
    String refId = "";

    public HomeItem(String lat, String lon, String catName, String locationMame, String insertDate, String refId) {
        this.lat = lat;
        this.lon = lon;
        this.catName = catName;
        this.locationMame = locationMame;
        this.insertDate = insertDate;
        this.refId=  refId;


    }

    public HomeItem() {

    }

    public HomeItem(String lat, String lon, String catName, String locationMame, String insertDate, String salaryFromTo, int salaryOrRent, boolean enabled) {
        this.lat = lat;
        this.lon = lon;
        this.catName = catName;
        this.locationMame = locationMame;
        this.insertDate = insertDate;
        this.salaryFromTo = salaryFromTo;
        this.salaryOrRent = salaryOrRent;
        this.enabled = enabled;
    }

    public String getRefId() {
        return refId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }

    public String getSalaryFromTo() {
        return salaryFromTo;
    }

    public void setSalaryFromTo(String salaryFromTo) {
        this.salaryFromTo = salaryFromTo;
    }

    public int getSalaryOrRent() {
        return salaryOrRent;
    }

    public void setSalaryOrRent(int salaryOrRent) {
        this.salaryOrRent = salaryOrRent;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(String insertDate) {
        this.insertDate = insertDate;
    }

    public String getLat() {

        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public String getLocationMame() {
        return locationMame;
    }

    public void setLocationMame(String locationMame) {
        this.locationMame = locationMame;
    }
}
