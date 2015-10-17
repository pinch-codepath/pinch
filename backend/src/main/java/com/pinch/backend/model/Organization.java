package com.pinch.backend.model;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.GeoPt;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PhoneNumber;

public class Organization {
    private Long id;
    private String name;
    private String address;
    private PhoneNumber phoneNumber;
    private GeoPt location;
    private String url;

    public static Entity toEntity(Organization organization) {
        Entity entity = new Entity(Constants.ORGANIZATION);
        String name = organization.getName();
        if(name != null) {
            entity.setProperty("name", name);
        }
        String address = organization.getAddress();
        if(address != null) {
            entity.setProperty("address", address);
        }
        PhoneNumber phoneNumber = organization.getPhoneNumber();
        if(phoneNumber != null) {
            entity.setProperty("phoneNumber", phoneNumber);
        }
        GeoPt location = organization.getLocation();
        if(location != null) {
            entity.setProperty("location", location);
        }
        String url = organization.getUrl();
        if(url != null) {
            entity.setProperty("url", url);
        }
        return entity;
    }

    public static Organization fromEntity(Entity entity) {
        Organization organization = new Organization();
        Key key = entity.getKey();
        if (key != null) {
            organization.setId(key.getId());
        }
        Object name = entity.getProperty("name");
        if (name != null) {
            organization.setName((String) name);
        }
        Object address = entity.getProperty("address");
        if (address != null) {
            organization.setAddress((String) address);
        }
        Object phoneNumber = entity.getProperty("phoneNumber");
        if (address != null) {
            organization.setPhoneNumber((PhoneNumber) phoneNumber);
        }
        Object location = entity.getProperty("location");
        if (location != null) {
            organization.setLocation((GeoPt) location);
        }
        Object url = entity.getProperty("url");
        if (url != null) {
            organization.setUrl((String) url);
        }
        return organization;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public GeoPt getLocation() {
        return location;
    }

    public void setLocation(GeoPt location) {
        this.location = location;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(PhoneNumber phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
