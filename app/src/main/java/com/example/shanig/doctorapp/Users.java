package com.example.shanig.doctorapp;

/**
 * Created by ShaniG on 7/17/2017.
 */

public class Users {

    private String email,password,gender,userType;
    private int year,month,day;

    public Users(){

    }

    public Users(String email, String password, String gender, String userType) {
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.userType = userType;
    }

    public Users(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getGender() {
        return gender;
    }

    public String getUserType() {
        return userType;
    }
}
