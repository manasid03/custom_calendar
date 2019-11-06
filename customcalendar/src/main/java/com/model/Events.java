package com.model;

import java.util.Date;

public class Events {
    private Date eventDate;
    private boolean Status;
    private String title;

    public Events(Date eventDate, boolean status,String title) {
        this.eventDate = eventDate;
        Status = status;
        this.title = title;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public boolean isStatus() {
        return Status;
    }

    public void setStatus(boolean status) {
        Status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
