package com.example.vizora;

import java.util.Date;

public class Meter {
    private String documentId;
    private String owner;
    private String address;
    private float latestValue;
    private Date latestDate;
    private Date deadline;

    public Meter() { }

    public Meter(String documentId, String owner, String address, float latestValue, Date latestDate, Date deadline) {
        this.documentId = documentId;
        this.owner = owner;
        this.address = address;
        this.latestValue = latestValue;
        this.latestDate = latestDate;
        this.deadline = deadline;
    }

    public Meter(String owner, String address, float latestValue, Date latestDate, Date deadline) {
        this.owner = owner;
        this.address = address;
        this.latestValue = latestValue;
        this.latestDate = latestDate;
        this.deadline = deadline;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getOwner() { return owner; }

    public String getAddress() { return address; }

    public float getLatestValue() { return latestValue; }

    public Date getLatestDate() { return latestDate; }

    public Date getDeadline() { return deadline; }
}
