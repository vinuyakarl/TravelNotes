package com.example.travelnotes.main.entity;

import java.time.LocalTime;
import java.util.Date;

public class Itinerary {
    private LocalTime time;
    private String description;
    private Float cost;
    private Date date;

    public Itinerary(Date date, LocalTime time, String description, Float cost) {
        this.date = date;
        this.time = time;
        this.description = description;
        this.cost = cost;
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

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
