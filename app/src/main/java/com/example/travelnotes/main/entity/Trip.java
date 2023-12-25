package com.example.travelnotes.main.entity;

import android.util.Log;

import com.example.travelnotes.main.control.ItineraryDB;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class Trip implements Serializable {
    private String destination;
    private String origin;
    private UniqueID uniqueId;
    private Date tripStarted;
    private Date tripEnded;
    private Float cost;
    private ArrayList<Itinerary> itineraries = new ArrayList<>();

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

    public ArrayList<Itinerary> getItineraries() {
        return itineraries;
    }

    public void setItineraries(ArrayList<Itinerary> itineraries) {
        this.itineraries = itineraries;
    }

    public void addItinerary(Itinerary itinerary) {
        itineraries.add(itinerary);
        Log.d("testing", "why can't we get here");
        ItineraryDB itineraryDB = new ItineraryDB();
        itineraryDB.addItineraryToTripDB(this, itinerary);
    }
}
