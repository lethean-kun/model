package com.uhetrip.api.dto;

import com.uhetrip.api.dto.common.UheReq;

public class UheOrderPayReq extends UheReq {
    private String sessionid;
    private Param param;
    

   public String getSessionid() {
        return sessionid;
    }


    public void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }


public Param getParam() {
        return param;
    }


    public void setParam(Param param) {
        this.param = param;
    }


public class Param {

       private String data;
       private String orderNo;
       private Integer payType;
       private Double totalPrice;
       public void setData(String data) {
            this.data = data;
        }
        public String getData() {
            return data;
        }

       public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }
        public String getOrderNo() {
            return orderNo;
        }

  
        public Integer getPayType() {
            return payType;
        }
        public void setPayType(Integer payType) {
            this.payType = payType;
        }
        public Double getTotalPrice() {
            return totalPrice;
        }
        public void setTotalPrice(Double totalPrice) {
            this.totalPrice = totalPrice;
        }

     

   }
}
