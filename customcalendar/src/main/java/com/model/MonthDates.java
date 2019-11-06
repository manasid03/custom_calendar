package com.model;

import androidx.annotation.NonNull;

import com.model.Events;

import java.util.ArrayList;
import java.util.Calendar;

public class MonthDates {

    private Calendar calendar;
    private int dateOf;
    private int disable;
    private Boolean event;
    private ArrayList<Events> eventList;

    public MonthDates(Calendar calendar, int dateOf, int disable, Boolean event) {
        this.calendar = calendar;
        this.dateOf = dateOf;
        this.disable = disable;
        this.event = event;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    public int getDateOf() {
        return dateOf;
    }

    public void setDateOf(int dateOf) {
        this.dateOf = dateOf;
    }

    public int getDisable() {
        return disable;
    }

    public void setDisable(int disable) {
        this.disable = disable;
    }

    public Boolean getEvent() {
        return event;
    }

    public void setEvent(Boolean event) {
        this.event = event;
    }

    public ArrayList<Events> getEventList() {
        return eventList;
    }

    public void setEventList(ArrayList<Events> eventList) {
        this.eventList = eventList;
    }

    @NonNull
    @Override
    public String toString() {

        String monthDate = ""+calendar.getTime();
        return monthDate;
    }
}
