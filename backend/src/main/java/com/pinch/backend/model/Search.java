package com.pinch.backend.model;

import com.google.appengine.api.datastore.GeoPt;

import java.util.Date;

public class Search {
    private String text;

    private Date startTime;

    private Date endTime;

    private GeoPt currentLocation;

    private int distanceInMeters;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public GeoPt getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(GeoPt currentLocation) {
        this.currentLocation = currentLocation;
    }

    public int getDistanceInMeters() {
        return distanceInMeters;
    }

    public void setDistanceInMeters(int distanceInMeters) {
        this.distanceInMeters = distanceInMeters;
    }
}
