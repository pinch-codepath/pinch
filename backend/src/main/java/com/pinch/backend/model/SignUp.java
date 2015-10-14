package com.pinch.backend.model;

import com.google.appengine.api.datastore.Rating;

public class SignUp {
    private String userId;
    private String eventId;
    private boolean checkIn;
    private Rating volunteerRatingEvent;
    private Rating orgRatingVolunteer;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public boolean isCheckIn() {
        return checkIn;
    }

    public void setCheckIn(boolean checkIn) {
        this.checkIn = checkIn;
    }

    public Rating getVolunteerRatingEvent() {
        return volunteerRatingEvent;
    }

    public void setVolunteerRatingEvent(Rating volunteerRatingEvent) {
        this.volunteerRatingEvent = volunteerRatingEvent;
    }

    public Rating getOrgRatingVolunteer() {
        return orgRatingVolunteer;
    }

    public void setOrgRatingVolunteer(Rating orgRatingVolunteer) {
        this.orgRatingVolunteer = orgRatingVolunteer;
    }
}
