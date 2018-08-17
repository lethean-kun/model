package com.uhetrip.api.dto.common;

public class PsgInfo {

    private Integer psgType;
    private String countryCode;
    private String idType;
    private String idValidTo;
    private String idNo;
    private String birthDate;
    private String psgName;
    private String phoneNo;
    private String idIssuePlace;
    private String gender;

    private String ticketNo;

    public String getTicketNo() {
        return ticketNo;
    }

    public void setTicketNo(String ticketNo) {
        this.ticketNo = ticketNo;
    }

    public void setPsgType(Integer psgType) {
        this.psgType = psgType;
    }

    public Integer getPsgType() {
        return psgType;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdValidTo(String idValidTo) {
        this.idValidTo = idValidTo;
    }

    public String getIdValidTo() {
        return idValidTo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setPsgName(String psgName) {
        this.psgName = psgName;
    }

    public String getPsgName() {
        return psgName;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setIdIssuePlace(String idIssuePlace) {
        this.idIssuePlace = idIssuePlace;
    }

    public String getIdIssuePlace() {
        return idIssuePlace;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }

}
