package com.example.shanig.doctorapp;

/**
 * Created by ShaniG on 8/7/2017.
 */

public class certificate {

    private String certificateTitle,certifiedFrom,certificationYear;

    public certificate(){

    }

    public certificate(String certificateTitle, String certifiedFrom, String certificationYear) {
        this.certificateTitle = certificateTitle;
        this.certifiedFrom = certifiedFrom;
        this.certificationYear = certificationYear;
    }

    public certificate(String certificateTitle, String certifiedFrom) {
        this.certificateTitle = certificateTitle;
        this.certifiedFrom = certifiedFrom;
    }

    public String getCertificateTitle() {
        return certificateTitle;
    }

    public String getCertifiedFrom() {
        return certifiedFrom;
    }

    public String getCertificationYear() {
        return certificationYear;
    }

    public void setCertificateTitle(String certificateTitle) {
        this.certificateTitle = certificateTitle;
    }

    public void setCertifiedFrom(String certifiedFrom) {
        this.certifiedFrom = certifiedFrom;
    }

    public void setCertificationYear(String certificationYear) {
        this.certificationYear = certificationYear;
    }
}
