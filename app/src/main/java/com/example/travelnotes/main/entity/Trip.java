package com.example.travelnotes.main.entity;

import java.util.UUID;

public class Trip {
    private String destination;
    private String origin;
    private UniqueID uniqueId;

    public Trip(String destination, String origin) {
        this.destination = destination;
        this.origin = origin;
        this.uniqueId = new UniqueID();
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
}
