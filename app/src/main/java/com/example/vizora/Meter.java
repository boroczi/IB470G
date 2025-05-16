package com.example.vizora;

import java.util.Date;

public class Meter {
    //private String id;
    private String address;
    private float latestValue;
    private Date latestDate;
    private Date deadline;

    public Meter() { }

    public Meter(String address, float latestValue, Date latestDate, Date deadline) {
        //this.id = id;
        this.address = address;
        this.latestValue = latestValue;
        this.latestDate = latestDate;
        this.deadline = deadline;
    }

    //public String getId() { return id; }

    public String getAddress() { return address; }

    public float getLatestValue() { return latestValue; }

    public Date getLatestDate() { return latestDate; }

    public Date getDeadline() { return deadline; }
}
