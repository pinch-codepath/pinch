package com.pinch.backend.model;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;

import java.util.Date;

public class Event {

    // TODO: skills, images

    private Long id;
    private String title;
    private String description;
    private Organization organization;
    private Date startTime;
    private Date endTime;
    private String url;

    public static Entity toEntity(Event event) {
        Entity entity = new Entity(Constants.EVENT);
        String title = event.getTitle();
        if(title != null) {
            entity.setProperty("title", title);
        }
        String description = event.getDescription();
        if(description != null) {
            entity.setProperty("description", description);
        }
        Date startTime = event.getStartTime();
        if(startTime != null) {
            entity.setProperty("startTime", startTime);
        }
        Date endTime = event.getEndTime();
        if(endTime != null) {
            entity.setProperty("endTime", endTime);
        }
        return entity;
    }

    public static Event fromEntity(Entity entity) {
        Event event = new Event();
        Key key = entity.getKey();
        if (key != null) {
            event.setId(key.getId());
        }
        Object title = entity.getProperty("title");
        if (title != null) {
            event.setTitle((String) title);
        }
        Object description = entity.getProperty("description");
        if (description != null) {
            event.setDescription((String) description);
        }
        Object startTime = entity.getProperty("startTime");
        if (startTime != null) {
            event.setStartTime((Date) startTime);
        }
        Object endTime = entity.getProperty("endTime");
        if (endTime != null) {
            event.setEndTime((Date) endTime);
        }
        return event;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
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


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}