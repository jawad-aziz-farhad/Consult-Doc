package com.example.shanig.doctorapp;

/**
 * Created by ShaniG on 8/7/2017.
 */

public class specialize {

    private String specializationTitle,specializationInstitute,completionYear;
    public specialize(){

    }

    public specialize(String specializationTitle, String specializationInstitute, String completionYear) {
        this.specializationTitle = specializationTitle;
        this.specializationInstitute = specializationInstitute;
        this.completionYear = completionYear;
    }

    public specialize(String specializationTitle, String specializationInstitute) {
        this.specializationTitle = specializationTitle;
        this.specializationInstitute = specializationInstitute;
    }

    public String getSpecializationTitle() {
        return specializationTitle;
    }

    public String getSpecializationInstitute() {
        return specializationInstitute;
    }

    public String getCompletionYear() {
        return completionYear;
    }

    public void setSpecializationTitle(String specializationTitle) {
        this.specializationTitle = specializationTitle;
    }

    public void setSpecializationInstitute(String specializationInstitute) {
        this.specializationInstitute = specializationInstitute;
    }

    public void setCompletionYear(String completionYear) {
        this.completionYear = completionYear;
    }
}
