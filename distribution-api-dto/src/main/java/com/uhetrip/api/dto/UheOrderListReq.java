package com.uhetrip.api.dto;

import com.uhetrip.api.dto.common.UheReq;

/**
 * @author zongzekun
 * @Date 2018年7月13日 上午11:14:09
 */
public class UheOrderListReq extends UheReq {

    /**
     * param : {"flightNo":"","pnrCode":"","ticketNo":"","endDate":"","orderID":"","psgName":"","airlineCode":"","startDate":"","status":""}
     * pageIndex :
     * pageSize :
     */
    private ParamEntity param;
    private Integer pageIndex;
    private Integer pageSize;

    public void setParam(ParamEntity param) {
        this.param = param;
    }



    public ParamEntity getParam() {
        return param;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public class ParamEntity {
        /**
         * flightNo :
         * pnrCode :
         * ticketNo :
         * endDate :
         * orderID :
         * psgName :
         * airlineCode :
         * startDate :
         * status :
         */
        private String flightNo;
        private String pnrCode;
        private String ticketNo;
        private String endDate;
        private String orderID;
        private String psgName;
        private String airlineCode;
        private String startDate;
        private Integer status;

        public void setFlightNo(String flightNo) {
            this.flightNo = flightNo;
        }

        public void setPnrCode(String pnrCode) {
            this.pnrCode = pnrCode;
        }

        public void setTicketNo(String ticketNo) {
            this.ticketNo = ticketNo;
        }

        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }

        public void setOrderID(String orderID) {
            this.orderID = orderID;
        }

        public void setPsgName(String psgName) {
            this.psgName = psgName;
        }

        public void setAirlineCode(String airlineCode) {
            this.airlineCode = airlineCode;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }


        public String getFlightNo() {
            return flightNo;
        }

        public String getPnrCode() {
            return pnrCode;
        }

        public String getTicketNo() {
            return ticketNo;
        }

        public String getEndDate() {
            return endDate;
        }

        public String getOrderID() {
            return orderID;
        }

        public String getPsgName() {
            return psgName;
        }

        public String getAirlineCode() {
            return airlineCode;
        }

        public String getStartDate() {
            return startDate;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }
    }
}
