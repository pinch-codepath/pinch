package com.pinch.backend.model;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;

import java.util.Comparator;
import java.util.Date;

import static com.pinch.backend.OfyService.ofy;

public class Event {

    private Long id;
    private String title;
    private String description;
    private Organization organization;
    private String displayUrl;
    private Date startTime;
    public static final Comparator<Event> COMPARE_START_TIME =
            new Comparator<Event>() {
                public int compare(Event e1, Event e2) {
                    return e1.getStartTime().compareTo(e2.getStartTime());
                }
            };
    private Date endTime;
    private String url;
    private String addressStreet;
    private String addressCity;
    private String addressState;
    private Long addressZip;
    private String addressNeighborhood;

    private String skill1;
    private String skill2;
    private String skill3;

    public static Entity toEntity(long organizationId, Event event) {
        Entity entity = new Entity(Constants.EVENT);
        entity.setProperty("organizationId", organizationId);
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
        String displayUrl = event.getDisplayUrl();
        if (displayUrl != null) {
            entity.setProperty("displayUrl", displayUrl);
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
        String skill1 = event.getSkill1();
        if (skill1 != null) {
            entity.setProperty("skill1", skill1);
        }
        String skill2 = event.getSkill2();
        if (skill2 != null) {
            entity.setProperty("skill2", skill2);
        }
        String skill3 = event.getSkill3();
        if (skill3 != null) {
            entity.setProperty("skill3", skill3);
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
        Object displayUrl = entity.getProperty("displayUrl");
        if (displayUrl != null) {
            event.setDisplayUrl((String) displayUrl);
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
        Object skill1 = entity.getProperty("skill1");
        if (skill1 != null) {
            event.setSkill1((String) skill1);
        }
        Object skill2 = entity.getProperty("skill2");
        if (skill2 != null) {
            event.setSkill2((String) skill2);
        }
        Object skill3 = entity.getProperty("skill3");
        if (skill3 != null) {
            event.setSkill3((String) skill3);
        }
        Long organizationId = (Long) entity.getProperty("organizationId");
        Organization organization = ofy()
                .load()
                .type(Organization.class)
                .id(organizationId)
                .now();
        event.setOrganization(organization);
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

    public String getSkill1() {
        return skill1;
    }

    public void setSkill1(String skill1) {
        this.skill1 = skill1;
    }

    public String getSkill2() {
        return skill2;
    }

    public void setSkill2(String skill2) {
        this.skill2 = skill2;
    }

    public String getSkill3() {
        return skill3;
    }

    public void setSkill3(String skill3) {
        this.skill3 = skill3;
    }

    public String getDisplayUrl() {
        return displayUrl;
    }

    public void setDisplayUrl(String displayUrl) {
        this.displayUrl = displayUrl;
    }
}