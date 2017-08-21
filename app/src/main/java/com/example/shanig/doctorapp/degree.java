package com.example.shanig.doctorapp;

/**
 * Created by ShaniG on 8/7/2017.
 */

public class degree {

    private String degreeTitle,DegreeInstitute,completionYear;

    public degree(){

    }

    public degree(String degreeTitle, String degreeInstitute, String completionYear) {
        this.degreeTitle = degreeTitle;
        DegreeInstitute = degreeInstitute;
        this.completionYear = completionYear;
    }

    public degree(String degreeTitle, String degreeInstitute) {
        this.degreeTitle = degreeTitle;
        DegreeInstitute = degreeInstitute;
    }

    public void setDegreeTitle(String degreeTitle) {
        this.degreeTitle = degreeTitle;
    }

    public void setDegreeInstitute(String degreeInstitute) {
        DegreeInstitute = degreeInstitute;
    }

    public void setCompletionYear(String completionYear) {
        this.completionYear = completionYear;
    }

    public String getDegreeTitle() {
        return degreeTitle;
    }

    public String getDegreeInstitute() {
        return DegreeInstitute;
    }

    public String getCompletionYear() {
        return completionYear;
    }
}
