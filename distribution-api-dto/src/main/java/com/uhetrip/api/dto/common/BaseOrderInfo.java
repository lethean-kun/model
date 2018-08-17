package com.uhetrip.api.dto.common;

public class BaseOrderInfo{
    /**
     * penaltyRule :
     * orderNo :
     * payType :
     * pnrCode :
     * payTime :
     * createTime :
     * TotalPrice :
     * status :
     */
    private String penaltyRule;
    private String orderNo;
    private Integer payType;
    private String pnrCode;
    private String payTime;
    private String createTime;
    private Double totalPrice;
    private Integer status;

    private Integer flightType;

    public Integer getPayType() {
        return payType;
    }

    public Integer getStatus() {
        return status;
    }

    public Integer getFlightType() {
        return flightType;
    }

    public void setFlightType(Integer flightType) {
        this.flightType = flightType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setPenaltyRule(String penaltyRule) {
        this.penaltyRule = penaltyRule;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }



    public void setPnrCode(String pnrCode) {
        this.pnrCode = pnrCode;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }



    public String getPenaltyRule() {
        return penaltyRule;
    }

    public String getOrderNo() {
        return orderNo;
    }



    public String getPnrCode() {
        return pnrCode;
    }

    public String getPayTime() {
        return payTime;
    }

    public String getCreateTime() {
        return createTime;
    }

}