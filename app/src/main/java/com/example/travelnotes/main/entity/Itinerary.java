package com.example.travelnotes.main.entity;

import java.time.LocalTime;
import java.util.Date;
import java.util.UUID;

public class Itinerary {
    private LocalTime timeStart;
    private LocalTime timeEnd;
    private String activity;
    private String location;
    private Float cost;
    private Date date;
    private UniqueID uniqueID;

    public Itinerary(Date date, LocalTime timeStart, LocalTime timeEnd, String location, String activity, Float cost) {
        this.date = date;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.location = location;
        this.activity = activity;
        this.cost = cost;
        this.uniqueID = new UniqueID();
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

    public LocalTime getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(LocalTime timeStart) {
        this.timeStart = timeStart;
    }

    public LocalTime getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(LocalTime timeEnd) {
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

    public UUID getUniqueID() {
        return uniqueID.getID();
    }

    public void setUniqueID(UniqueID uniqueID) {
        this.uniqueID = uniqueID;
    }
}
