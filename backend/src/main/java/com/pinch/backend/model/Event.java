package com.pinch.backend.model;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;

import java.util.Comparator;
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
    private String addressStreet;
    private String addressCity;
    private String addressState;
    private Long addressZip;
    private String addressNeighborhood;


    public static Entity toEntity(Key parentKey, Event event) {
        Entity entity = new Entity(Constants.EVENT, parentKey);
        String title = event.getTitle();
        if (title != null) {
            entity.setProperty("title", title);
        }
        String description = event.getDescription();
        if (description != null) {
            entity.setProperty("description", description);
        }
        Date startTime = event.getStartTime();
        if (startTime != null) {
            entity.setProperty("startTime", startTime);
        }
        Date endTime = event.getEndTime();
        if (endTime != null) {
            entity.setProperty("endTime", endTime);
        }
        String addressStreet = event.getAddressStreet();
        if (addressStreet != null) {
            entity.setProperty("addressStreet", addressStreet);
        }
        String addressCity = event.getAddressCity();
        if (addressCity != null) {
            entity.setProperty("addressCity", addressCity);
        }
        String addressState = event.getAddressState();
        if (addressState != null) {
            entity.setProperty("addressState", addressState);
        }
        Long addressZip = event.getAddressZip();
        if (addressStreet != null) {
            entity.setProperty("addressZip", addressZip);
        }
        String addressNeighborhood = event.getAddressNeighborhood();
        if (addressStreet != null) {
            entity.setProperty("addressNeighborhood", addressNeighborhood);
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
        Object addressStreet = entity.getProperty("addressStreet");
        if (addressStreet != null) {
            event.setAddressStreet((String) addressStreet);
        }
        Object addressState = entity.getProperty("addressState");
        if (addressState != null) {
            event.setAddressState((String) addressState);
        }
        Object addressCity = entity.getProperty("addressCity");
        if (addressCity != null) {
            event.setAddressCity((String) addressCity);
        }
        Object addressZip = entity.getProperty("addressZip");
        if (addressZip != null) {
            event.setAddressZip((Long) addressZip);
        }
        Object addressNeighborhood = entity.getProperty("addressNeighborhood");
        if (addressNeighborhood != null) {
            event.setAddressNeighborhood((String) addressNeighborhood);
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

    public String getAddressStreet() {
        return addressStreet;
    }

    public void setAddressStreet(String addressStreet) {
        this.addressStreet = addressStreet;
    }

    public String getAddressCity() {
        return addressCity;
    }

    public void setAddressCity(String addressCity) {
        this.addressCity = addressCity;
    }

    public String getAddressState() {
        return addressState;
    }

    public void setAddressState(String addressState) {
        this.addressState = addressState;
    }

    public Long getAddressZip() {
        return addressZip;
    }

    public void setAddressZip(Long addressZip) {
        this.addressZip = addressZip;
    }

    public String getAddressNeighborhood() {
        return addressNeighborhood;
    }

    public void setAddressNeighborhood(String addressNeighborhood) {
        this.addressNeighborhood = addressNeighborhood;
    }

    public static final Comparator<Event> COMPARE_START_TIME =
            new Comparator<Event>() {
                public int compare(Event e1, Event e2) {
                    return e2.getStartTime().compareTo(e1.getStartTime());
                }
            };
}