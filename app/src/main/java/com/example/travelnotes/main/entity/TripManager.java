package com.example.travelnotes.main.entity;

import java.util.ArrayList;

public class TripManager {
    private static final TripManager instance = new TripManager();
    private ArrayList<Trip> trips = new ArrayList<>();

    private TripManager() {
        super();
    }

    private static TripManager getInstance() {
        return instance;
    }
}
