package com.example.shanig.doctorapp;

/**
 * Created by ShaniG on 8/21/2017.
 */

public class SetTimings {

    String start,end;
    String monday,tuesday,wednesday,thursday,friday,saturday,sunday;

    public SetTimings(){

    }

    public SetTimings(String start, String end, String monday, String tuesday, String wednesday, String thursday, String friday, String saturday, String sunday) {
        this.start = start;
        this.end = end;
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
        this.saturday = saturday;
        this.sunday = sunday;
    }

    public SetTimings(String monday) {
        this.monday = monday;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }

    public String getMonday() {
        return monday;
    }

    public String getTuesday() {
        return tuesday;
    }

    public String getWednesday() {
        return wednesday;
    }

    public String getThursday() {
        return thursday;
    }

    public String getFriday() {
        return friday;
    }

    public String getSaturday() {
        return saturday;
    }

    public String getSunday() {
        return sunday;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public void setMonday(String monday) {
        this.monday = monday;
    }

    public void setTuesday(String tuesday) {
        this.tuesday = tuesday;
    }

    public void setWednesday(String wednesday) {
        this.wednesday = wednesday;
    }

    public void setThursday(String thursday) {
        this.thursday = thursday;
    }

    public void setFriday(String friday) {
        this.friday = friday;
    }

    public void setSaturday(String saturday) {
        this.saturday = saturday;
    }

    public void setSunday(String sunday) {
        this.sunday = sunday;
    }
}
