package com.example.travelnotes.main.entity;

public class User {
    private String username;
    private TripManager tripManager;

    public User(String username, TripManager tripManager) {
        this.username = username;
        this.tripManager = tripManager;
    }
}
