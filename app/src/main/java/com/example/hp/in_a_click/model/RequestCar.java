package com.example.hp.in_a_click.model;

/**
 * Created by ahmed on 10/12/17.
 */

public class RequestCar {
    String destLat, destLon, destName, customerId = "";

    public RequestCar(String destLat, String destLon, String destName) {
        this.destLat = destLat;
        this.destLon = destLon;
        this.destName = destName;
    }


    public RequestCar() {
    }

    public String getDestLat() {
        return destLat;
    }

    public void setDestLat(String destLat) {
        this.destLat = destLat;
    }

    public String getDestLon() {
        return destLon;
    }

    public void setDestLon(String destLon) {
        this.destLon = destLon;
    }

    public String getDestName() {
        return destName;
    }

    public void setDestName(String destName) {
        this.destName = destName;
    }
}
