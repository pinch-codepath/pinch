package com.pinch.backend.model;

import com.google.appengine.api.datastore.GeoPt;
import com.google.appengine.api.datastore.PhoneNumber;

import com.googlecode.objectify.annotation.Id;

@com.googlecode.objectify.annotation.Entity
public class Organization {
    @Id
    Long id;
    String name;
    String address;
    PhoneNumber phoneNumber;
    String displayUrl;
    GeoPt location;
    String url;

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

    public String getDisplayUrl() {
        return displayUrl;
    }

    public void setDisplayUrl(String displayUrl) {
        this.displayUrl = displayUrl;
    }
}
