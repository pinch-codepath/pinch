package com.pinch.backend.model;

import com.google.appengine.api.datastore.GeoPt;

import java.util.Date;

public class Event {

    private Long id;
    private String title;
    private String description;
    private String organization;
    private Date startTime;
    private Date endTime;
    private GeoPt location;

    public String getOrganization() {
        return organization;
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

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public GeoPt getLocation() {
        return location;
    }

    public void setLocation(GeoPt location) {
        this.location = location;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}