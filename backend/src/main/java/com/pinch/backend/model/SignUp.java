package com.pinch.backend.model;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;

public class SignUp {
    private long id;
    private long userId;
    private long eventId;
    private boolean checkedIn;

    public static SignUp fromEntity(Entity entity) {
        SignUp signUp = new SignUp();
        Key key = entity.getKey();
        if (key != null) {
            signUp.setId(key.getId());
        }
        Long userId = (Long) entity.getProperty("userId");
        if (userId != null) {
            signUp.setUserId(userId);
        }
        Long eventId = (Long) entity.getProperty("eventId");
        if (eventId != null) {
            signUp.setEventId(eventId);
        }
        Boolean checkedIn = (Boolean) entity.getProperty("checkedIn");
        if (checkedIn != null) {
            signUp.setCheckedIn(checkedIn);
        }
        return signUp;
    }

    public static Entity toEntity(SignUp signUp) {
        Entity entity = new Entity(Constants.SIGNUP);
        Long id = signUp.getId();
        if (id != null) {
            entity.setProperty("id", id);
        }
        Long userId = signUp.getUserId();
        if (userId != null) {
            entity.setProperty("userId", userId);
        }
        Long eventId = signUp.getEventId();
        if (eventId != null) {
            entity.setProperty("eventId", eventId);
        }
        Boolean checkedIn = signUp.isCheckedIn();
        if (checkedIn != null) {
            entity.setProperty("checkedIn", checkedIn);
        }
        return entity;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getEventId() {
        return eventId;
    }

    public void setEventId(long eventId) {
        this.eventId = eventId;
    }

    public boolean isCheckedIn() {
        return checkedIn;
    }

    public void setCheckedIn(boolean checkedIn) {
        this.checkedIn = checkedIn;
    }
}
