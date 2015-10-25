package com.pinch.backend.model;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;

public class User {
    private long key;
    private String id;
    private String name;
    private String emailAddress;
    private String authSource;
    private String twitterHandle;

    public static User fromEntity(Entity entity) {
        User user = new User();
        Key key = entity.getKey();
        if (key != null) {
            user.setKey(key.getId());
        }
        Object id = entity.getProperty("id");
        if (id != null) {
            user.setId((String) id);
        }
        Object authSource = entity.getProperty("authSource");
        if (authSource != null) {
            user.setAuthSource((String) authSource);
        }
        Object name = entity.getProperty("name");
        if (name != null) {
            user.setName((String) name);
        }
        return user;
    }

    public static Entity toEntity(User user) {
        Entity entity = null;
        if(user.getKey() > 0) {
            entity = new Entity(Constants.USER, user.getKey());
        } else {
            entity = new Entity(Constants.USER);
        }
        String id = user.getId();
        if (id != null) {
            entity.setProperty("id", id);
        }
        String authSource = user.getAuthSource();
        if (authSource != null) {
            entity.setProperty("authSource", authSource);
        }
        String name = user.getName();
        if (name != null) {
            entity.setProperty("name", name);
        }
        return entity;
    }

    public String getTwitterHandle() {
        return twitterHandle;
    }

    public void setTwitterHandle(String twitterHandle) {
        this.twitterHandle = twitterHandle;
    }

    public String getAuthSource() {
        return authSource;
    }

    public void setAuthSource(String authSource) {
        this.authSource = authSource;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getKey() {
        return key;
    }

    public void setKey(long key) {
        this.key = key;
    }
    // type
}
