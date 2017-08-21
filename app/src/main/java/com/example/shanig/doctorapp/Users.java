package com.example.shanig.doctorapp;

/**
 * Created by ShaniG on 7/17/2017.
 */

public class Users {

    private String firstName, lastName, gender, userType, experience, contact, address, city, country, dob,category,imagePath;
    private String startingTime,EndingTime;
    private double locationLati,locationLongi;

    public Users() {

    }

    public Users(String firstName, String lastName, String gender, String userType, String experience, String contact, String address, String city, String country, String dob,String category, double locationLati,double locationLongi, String imagePath) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.userType = userType;
        this.experience = experience;
        this.contact = contact;
        this.address = address;
        this.city = city;
        this.country = country;
        this.dob = dob;
        this.category = category;
        this.locationLongi = locationLongi;
        this.locationLati = locationLati;
        this.imagePath = imagePath;
    }

    /*public Users(String gender, String userType, String experience, String contact, String address, String city, String country, String dob) {
        this.gender = gender;
        this.userType = userType;
        this.experience = experience;
        this.contact = contact;
        this.address = address;
        this.city = city;
        this.country = country;
        this.dob = dob;
    }*/

    public Users(String firstName,String lastName,String gender, String userType, String dob) {
        this.gender = gender;
        this.userType = userType;
        this.dob = dob;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Users(String firstName, String lastName, String experience, String contact, String address, String city, String country) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.experience = experience;
        this.contact = contact;
        this.address = address;
        this.city = city;
        this.country = country;
    }
    public Users(double locationLati,double locationLongi) {
        this.locationLati = locationLati;
        this.locationLongi = locationLongi;
    }

    public Users(String firstName, String lastName, String address, String city, String country, String gender, String imagePath,String category) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.address = address;
        this.city = city;
        this.country = country;
        this.imagePath = imagePath;
        this.category = category;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getLastName() {
        return lastName;
    }

    public String getGender() {
        return gender;
    }

    public String getUserType() {
        return userType;
    }

    public String getExperience() {
        return experience;
    }

    public String getContact() {
        return contact;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getDob() {
        return dob;
    }

    public String getCategory() {
        return category;
    }

    public double getLocationLati() {
        return locationLati;
    }

    public double getLocationLongi() {
        return locationLongi;
    }

    public String getStartingTime() {
        return startingTime;
    }

    public String getEndingTime() {
        return EndingTime;
    }

    public void setStartingTime(String startingTime) {
        this.startingTime = startingTime;
    }

    public void setEndingTime(String endingTime) {
        EndingTime = endingTime;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setLocationLati(double locationLati) {
        this.locationLati = locationLati;
    }

    public void setLocationLongi(double locationLongi) {
        this.locationLongi = locationLongi;
    }
}