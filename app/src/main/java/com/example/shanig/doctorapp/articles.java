package com.example.shanig.doctorapp;

/**
 * Created by ShaniG on 7/25/2017.
 */

public class articles {


    private String time;
    private int id;
    private String title;
    private String descript;
    private String image;


    public articles() {
    }

    public articles(String title, String description, String image , String time){
        this.title = title;
        this.time = time;
        this.descript = description;
        this.image = image;
    }
    public articles(String time, int id, String title, String descript, String image) {
        this.time = time;
        this.id = id;
        this.title = title;
        this.descript = descript;
        this.image = image;
    }

    public articles(String title, String descript, String image) {
        this.title = title;
        this.descript = descript;
        this.image = image;
        }


    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public String getDescript() {
        return descript;
    }


    public String getTime() {
        return time;
    }

    public int getId() {
        return id;
    }
}

