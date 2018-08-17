package com.uhetrip.api.dto.common;

import java.util.List;

public class OrderInfo {

    private BaseOrderInfo baseOrderInfo;

    private List<FlightDetail> flightInfo;

    private List<PsgInfo> psgInfo;

    private List<PsgPriceInfo> psgPriceInfo;

    public List<FlightDetail> getFlightInfo() {
        return flightInfo;
    }

    public void setFlightInfo(List<FlightDetail> flightInfo) {
        this.flightInfo = flightInfo;
    }

    public List<PsgInfo> getPsgInfo() {
        return psgInfo;
    }

    public void setPsgInfo(List<PsgInfo> psgInfo) {
        this.psgInfo = psgInfo;
    }

    public List<PsgPriceInfo> getPsgPriceInfo() {
        return psgPriceInfo;
    }

    public void setPsgPriceInfo(List<PsgPriceInfo> psgPriceInfo) {
        this.psgPriceInfo = psgPriceInfo;
    }

    public void setBaseOrderInfo(BaseOrderInfo baseOrderInfo) {
        this.baseOrderInfo = baseOrderInfo;
    }

    public BaseOrderInfo getBaseOrderInfo() {
        return baseOrderInfo;
    }


}