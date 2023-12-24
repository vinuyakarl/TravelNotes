package com.example.travelnotes.main.entity;

import java.time.LocalTime;

public class Itinerary {
    private LocalTime time;
    private String description;

    public Itinerary(LocalTime time, String description) {
        this.time = time;
        this.description = description;
    }

    public Itinerary() {
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
