package com.example.travelnotes.main.entity;

import java.io.Serializable;
import java.util.UUID;

/**
 * Class that represents a uniqueID used by trips and itineraries. Basics and setters are found here.
 */
public class UniqueID implements Serializable {
    private UUID ID;
    private long msb;
    private long lsb;
    public UniqueID() {
        ID = UUID.randomUUID();
        msb = ID.getMostSignificantBits();
        lsb = ID.getLeastSignificantBits();
    }

    public String getID() {
        return ID.toString();
    }

    public void setID(UUID ID) {
        this.ID = ID;
    }

    public long getMsb() {
        return msb;
    }

    public void setMsb(long msb) {
        this.msb = msb;
    }

    public long getLsb() {
        return lsb;
    }

    public void setLsb(long lsb) {
        this.lsb = lsb;
    }
}
