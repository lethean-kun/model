package com.uhetrip.api.dto;

import com.uhetrip.api.dto.common.ErrorInfo;
import com.uhetrip.api.dto.common.UheRsp;

public class UheOrderPayRsp extends UheRsp {
    private PayInfo payInfo;
    private ErrorInfo errorInfo;

    public PayInfo getPayInfo() {
        return payInfo;
    }

    public void setPayInfo(PayInfo payInfo) {
        this.payInfo = payInfo;
    }

    public ErrorInfo getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(ErrorInfo errorInfo) {
        this.errorInfo = errorInfo;
    }

    public class PayInfo {

        private String orderNo;
        private Integer status;
        private Double totalPrice;
        private String tradeNo;

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }

        public String getOrderNo() {
            return orderNo;
        }

      

        public Integer getStatus() {
            return status;
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

        public void setTradeNo(String tradeNo) {
            this.tradeNo = tradeNo;
        }

        public String getTradeNo() {
            return tradeNo;
        }

    }
}
