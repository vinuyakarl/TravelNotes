package com.example.travelnotes.main.entity;

import java.time.LocalTime;
import java.util.Date;
import java.util.UUID;

public class Itinerary {
    private String timeStart;
    private String timeEnd;
    private String activity;
    private String location;
    private Float cost;
    private Date date;
    private String uniqueID;

    public Itinerary(Date date, String timeStart, String timeEnd, String location, String activity, Float cost) {
        this.date = date;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.location = location;
        this.activity = activity;
        this.cost = cost;
        this.uniqueID = new UniqueID().getID();
    }

    public Itinerary() {
    }

    public Float getCost() {
        return cost;
    }

    public void setCost(Float cost) {
        this.cost = cost;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getUniqueID() {
        return uniqueID;
    }

    public void setUniqueID(String uniqueID) {
        this.uniqueID = uniqueID;
    }
}
