package com.example.travelnotes.main.entity;

import android.util.Log;
import android.widget.ArrayAdapter;

import com.example.travelnotes.main.adapters.TripAdapter;
import com.example.travelnotes.main.control.ItineraryDB;
import com.example.travelnotes.main.control.TripManagerDB;

import java.util.ArrayList;

public class TripManager {
    private ArrayList<Trip> trips;
    private TripManagerDB tripManagerDB = new TripManagerDB();
    public TripManager() {
        trips = new ArrayList<>();
    }


    public Trip getTrip(int position) {
        return trips.get(position);
    }

    public void addTrip(Trip newTrip) {
        trips.add(newTrip);
        tripManagerDB.addTripToDB(newTrip);
    }

    public ArrayList<Trip> getTrips() {
        return trips;
    }

    public void setTrips(ArrayList<Trip> trips) {
        this.trips = trips;
    }

    public void fetchItineraries() {
        ItineraryDB itineraryDB = new ItineraryDB();
        itineraryDB.fetchItinerariesDB(this);
    }
}
