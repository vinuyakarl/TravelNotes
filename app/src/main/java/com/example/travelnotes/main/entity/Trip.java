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

/**
 * Represents a trip where destination, origin, id, date, cost, itineraries are stored. Basic getters
 * and setters, adding/editing/deleting itineraries, checking if an item matches a query, as well as
 * editing a trip function are located in this class.
 */
public class Trip implements Serializable {
    private String destination;
    private String origin;
    private String uniqueId;
    private Date tripStarted;
    private Date tripEnded;
    private Float cost;
    private ArrayList<Itinerary> itineraries = new ArrayList<>();

    public Trip(String destination, String origin, Date tripStarted, Date tripEnded, Float cost) {
        this.destination = destination;
        this.origin = origin;
        this.uniqueId = new UniqueID().getID();
        this.tripStarted = tripStarted;
        this.tripEnded = tripEnded;
        this.cost = cost;
    }

    public Trip() {
    }

    // getters and setters
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

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
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
        Float unofficialCost = 0f;
        for (Itinerary itinerary: this.itineraries) {
             unofficialCost += itinerary.getCost();
        }
        this.cost = unofficialCost;
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
        ItineraryDB itineraryDB = new ItineraryDB();
        itineraryDB.addItineraryToTripDB(this, itinerary);
    }

    public void editItinerary(Itinerary oldItinerary, Itinerary newItinerary)  {
        oldItinerary.editItinerary(newItinerary);
        ItineraryDB itineraryDB = new ItineraryDB();
        itineraryDB.editItineraryDB(this, oldItinerary);

    }

    public void deleteItinerary(Itinerary itinerary) {
        itineraries.remove(itinerary);
        ItineraryDB itineraryDB = new ItineraryDB();
        itineraryDB.deleteItineraryDB(this, itinerary);
    }

    /**
     * Checks if an item matches a given query
     * @param query: user given query
     * @return boolean: if item fits the query or not
     */
    public boolean matchesQuery(String query) {
        if (this.destination.toLowerCase().contains(query.toLowerCase())) {
            return true;
        }
        return false;
    }

    public void editTrip(Trip newTrip) {
        this.setDestination(newTrip.getDestination());
        this.setOrigin(newTrip.getOrigin());
        this.setTripStarted(newTrip.getTripStarted());
        this.setTripEnded(newTrip.getTripEnded());
        Log.d("Searching", "Befores" + this.getDestination());
    }


}
