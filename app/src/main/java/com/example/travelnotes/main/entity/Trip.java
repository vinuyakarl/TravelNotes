package com.example.travelnotes.main.entity;

import java.util.Date;
import java.util.UUID;

public class Trip {
    private String destination;
    private String origin;
    private UniqueID uniqueId;
    private Date tripStarted;
    private Date tripEnded;
    private Float cost;

    public Trip(String destination, String origin, Date tripStarted, Date tripEnded, Float cost) {
        this.destination = destination;
        this.origin = origin;
        this.uniqueId = new UniqueID();
        this.tripStarted = tripStarted;
        this.tripEnded = tripEnded;
        this.cost = cost;
    }

    public Trip() {
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public UUID getUniqueId() {
        return uniqueId.getID();
    }

    public void setUniqueId(UniqueID uniqueId) {
        this.uniqueId = uniqueId;
    }

    public Date getTripStarted() {
        return tripStarted;
    }

    public void setTripStarted(Date tripStarted) {
        this.tripStarted = tripStarted;
    }

    public Date getTripEnded() {
        return tripEnded;
    }

    public void setTripEnded(Date tripEnded) {
        this.tripEnded = tripEnded;
    }

    public Float getCost() {
        return cost;
    }

    public void setCost(Float cost) {
        this.cost = cost;
    }

}
