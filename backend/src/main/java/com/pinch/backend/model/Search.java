package com.pinch.backend.model;

import java.util.Date;

public class Search {
    private String text;

    private Date startTime;

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
}
