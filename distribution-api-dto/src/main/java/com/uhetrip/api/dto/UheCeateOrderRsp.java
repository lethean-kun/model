package com.uhetrip.api.dto;

import com.uhetrip.api.dto.common.ErrorInfo;
import com.uhetrip.api.dto.common.UheRsp;

public class UheCeateOrderRsp extends UheRsp {
    private String sessionid;
    private ErrorInfo errorInfo;
    private OrderInfo orderInfo;
    

   public String getSessionid() {
        return sessionid;
    }

    public void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }

public ErrorInfo getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(ErrorInfo errorInfo) {
        this.errorInfo = errorInfo;
    }

    public OrderInfo getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(OrderInfo orderInfo) {
        this.orderInfo = orderInfo;
    }


  public class OrderInfo {

      private String data;
      private Boolean isVerifyPolicy;
      private Boolean isChangePnr;
      private String orderId;
      private Double totalPrice;
      private String pnrCode;
      public void setData(String data) {
           this.data = data;
       }
       public String getData() {
           return data;
       }

     

      public Boolean getIsVerifyPolicy() {
        return isVerifyPolicy;
    }
    public void setIsVerifyPolicy(Boolean isVerifyPolicy) {
        this.isVerifyPolicy = isVerifyPolicy;
    }
    public Boolean getIsChangePnr() {
        return isChangePnr;
    }
    public void setIsChangePnr(Boolean isChangePnr) {
        this.isChangePnr = isChangePnr;
    }
    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
    public void setOrderId(String orderId) {
           this.orderId = orderId;
       }
       public String getOrderId() {
           return orderId;
       }

  

      public Double getTotalPrice() {
        return totalPrice;
    }
    public void setPnrCode(String pnrCode) {
           this.pnrCode = pnrCode;
       }
       public String getPnrCode() {
           return pnrCode;
       }

  }
}
