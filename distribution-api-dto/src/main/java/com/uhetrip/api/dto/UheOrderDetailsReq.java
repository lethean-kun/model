package com.uhetrip.api.dto;

import com.uhetrip.api.dto.common.UheReq;

/**
 * @author zongzekun
 * @Date 2018年7月13日 上午11:41:08
 */
public class UheOrderDetailsReq extends UheReq {


    /**
     *  param : {"orderID":""}
     */
    private paramEntity param;

    public paramEntity getParam() {
        return param;
    }

    public void setParam(paramEntity param) {
        this.param = param;
    }

    public class paramEntity {
        /**
         * orderID :
         */
        private String orderID;

        public void setOrderID(String orderID) {
            this.orderID = orderID;
        }

        public String getOrderID() {
            return orderID;
        }
    }
}
