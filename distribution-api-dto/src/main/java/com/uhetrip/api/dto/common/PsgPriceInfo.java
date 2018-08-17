package com.uhetrip.api.dto.common;

public class PsgPriceInfo {

    private Integer psgType;
    private Double baseFare;
    private Double tax;
    private Double totalFare;
    private Double discount;
    private Double addMoney;
    private Double ticketFee;

    public void setPsgType(Integer psgType) {
        this.psgType = psgType;
    }

    public Integer getPsgType() {
        return psgType;
    }

    public Double getBaseFare() {
        return baseFare;
    }

    public void setBaseFare(Double baseFare) {
        this.baseFare = baseFare;
    }

    public Double getTax() {
        return tax;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }

    public Double getTotalFare() {
        return totalFare;
    }

    public void setTotalFare(Double totalFare) {
        this.totalFare = totalFare;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Double getAddMoney() {
        return addMoney;
    }

    public void setAddMoney(Double addMoney) {
        this.addMoney = addMoney;
    }

    public Double getTicketFee() {
        return ticketFee;
    }

    public void setTicketFee(Double ticketFee) {
        this.ticketFee = ticketFee;
    }

 


}
