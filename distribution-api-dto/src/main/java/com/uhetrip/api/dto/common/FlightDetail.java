package com.uhetrip.api.dto.common;

public class FlightDetail {
    private String flightNo;
    private String cabinCode;
    private String depDateTime;
    private String arrDateTime;
    private String depAirport;
    private String arrAirport;
    private Boolean isCodeShare;
    private String operateAirline;
    private String depTerminal;
    private String arrTerminal;
    private String equipType;
    private String farebasis;
    private String cabinGrade;
    private Integer itineraryIndex;
    private Integer segmentIndex;
    private Luggage luggage;

    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }

    public String getFlightNo() {
        return flightNo;
    }

    public void setCabinCode(String cabinCode) {
        this.cabinCode = cabinCode;
    }

    public String getCabinCode() {
        return cabinCode;
    }

    public void setDepDateTime(String depDateTime) {
        this.depDateTime = depDateTime;
    }

    public String getDepDateTime() {
        return depDateTime;
    }

    public void setArrDateTime(String arrDateTime) {
        this.arrDateTime = arrDateTime;
    }

    public String getArrDateTime() {
        return arrDateTime;
    }

    public void setDepAirport(String depAirport) {
        this.depAirport = depAirport;
    }

    public String getDepAirport() {
        return depAirport;
    }

    public void setArrAirport(String arrAirport) {
        this.arrAirport = arrAirport;
    }

    public String getArrAirport() {
        return arrAirport;
    }

    public Boolean getIsCodeShare() {
        return isCodeShare;
    }

    public void setIsCodeShare(Boolean isCodeShare) {
        this.isCodeShare = isCodeShare;
    }

    public void setOperateAirline(String operateAirline) {
        this.operateAirline = operateAirline;
    }

    public String getOperateAirline() {
        return operateAirline;
    }

    public void setDepTerminal(String depTerminal) {
        this.depTerminal = depTerminal;
    }

    public String getDepTerminal() {
        return depTerminal;
    }

    public void setArrTerminal(String arrTerminal) {
        this.arrTerminal = arrTerminal;
    }

    public String getArrTerminal() {
        return arrTerminal;
    }

    public void setEquipType(String equipType) {
        this.equipType = equipType;
    }

    public String getEquipType() {
        return equipType;
    }

    public void setFarebasis(String farebasis) {
        this.farebasis = farebasis;
    }

    public String getFarebasis() {
        return farebasis;
    }

    public void setCabinGrade(String cabinGrade) {
        this.cabinGrade = cabinGrade;
    }

    public String getCabinGrade() {
        return cabinGrade;
    }

    public void setItineraryIndex(Integer itineraryIndex) {
        this.itineraryIndex = itineraryIndex;
    }

    public Integer getItineraryIndex() {
        return itineraryIndex;
    }

    public void setSegmentIndex(Integer segmentIndex) {
        this.segmentIndex = segmentIndex;
    }

    public Integer getSegmentIndex() {
        return segmentIndex;
    }

    public void setLuggage(Luggage luggage) {
        this.luggage = luggage;
    }

    public Luggage getLuggage() {
        return luggage;
    }

}
