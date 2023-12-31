package com.example.travelnotes.main.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

/**
 * Represents an itinerary of a trip where the id, activity, time, date, location, and cost are stored.
 * Getters and setters, editing, calculating combined date and time functions are located in this class.
 */
public class Itinerary implements Serializable {
    private String timeStart;
    private String timeEnd;
    private String activity;
    private String location;
    private Float cost;
    private Date date;
    private String uniqueID;
    private Date dateAndTime;

    public Itinerary(Date date, String timeStart, String timeEnd, String location, String activity, Float cost) {
        this.date = date;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.location = location;
        this.activity = activity;
        this.cost = cost;
        this.uniqueID = new UniqueID().getID();
        calculateDateAndTime();
    }

    public Itinerary() {
    }

    // Getters and Setters
    public Float getCost() {
        return cost;
    }

    public void setCost(Float cost) {
        this.cost = cost;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getUniqueID() {
        return uniqueID;
    }

    public void setUniqueID(String uniqueID) {
        this.uniqueID = uniqueID;
    }

    public void calculateDateAndTime() {
        Calendar newDate = Calendar.getInstance();
        LocalDate localDate = this.date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalTime localTime = LocalTime.parse(this.getTimeStart());
        newDate.set(localDate.getYear(), localDate.getMonthValue(), localDate.getDayOfMonth(), localTime.getHour(), localTime.getMinute());
        this.dateAndTime = newDate.getTime();
    }

    public Date getDateAndTime() {
        return dateAndTime;
    }

    /**
     * Edits old itinerary based on a given new itinerary whose properties are going to be used
     * @param newItinerary: itinerary with the newly updated properties
     */
    public void editItinerary(Itinerary newItinerary) {
        this.setDate(newItinerary.getDate());
        this.setTimeStart(newItinerary.getTimeStart());
        this.setTimeEnd(newItinerary.getTimeEnd());
        this.setLocation(newItinerary.getLocation());
        this.setCost(newItinerary.getCost());
        this.setActivity(newItinerary.getActivity());
        calculateDateAndTime();
    }
}
