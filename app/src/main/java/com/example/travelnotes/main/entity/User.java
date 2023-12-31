package com.example.travelnotes.main.entity;

/**
 * Represents a user of an app, who has a unique username, password, and its own tripManager.
 * Basic getters and setters are found here.
 */
public class User {
    private String username;
    private String password;
    private TripManager tripManager;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.tripManager = new TripManager();
    }

    public User() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public TripManager getTripManager() {
        return tripManager;
    }

    public void setTripManager(TripManager tripManager) {
        this.tripManager = tripManager;
    }
}
