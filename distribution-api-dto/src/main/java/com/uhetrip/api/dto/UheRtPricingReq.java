package com.uhetrip.api.dto;

import java.util.List;

import com.uhetrip.api.dto.common.PsgInfo;
import com.uhetrip.api.dto.common.UheReq;

public class UheRtPricingReq extends UheReq {
    private ControllerParam controllerParam;
    private PnrMsg pnrMsg;
    private List<PsgInfo> psgInfo;

    public ControllerParam getControllerParam() {
        return controllerParam;
    }

    public void setControllerParam(ControllerParam controllerParam) {
        this.controllerParam = controllerParam;
    }

    public PnrMsg getPnrMsg() {
        return pnrMsg;
    }

    public void setPnrMsg(PnrMsg pnrMsg) {
        this.pnrMsg = pnrMsg;
    }

    public List<PsgInfo> getPsgInfo() {
        return psgInfo;
    }

    public void setPsgInfo(List<PsgInfo> psgInfo) {
        this.psgInfo = psgInfo;
    }



    public class ControllerParam {

        private Boolean isChangePnr;
        private Boolean isOverseaEt;
        private Boolean isVerifyPolicy;
        private Boolean isBestCabin;

        public void setIsChangePnr(Boolean isChangePnr) {
            this.isChangePnr = isChangePnr;
        }

        public Boolean getIsChangePnr() {
            return isChangePnr;
        }

        public void setIsOverseaEt(Boolean isOverseaEt) {
            this.isOverseaEt = isOverseaEt;
        }

        public Boolean getIsOverseaEt() {
            return isOverseaEt;
        }

        public void setIsVerifyPolicy(Boolean isVerifyPolicy) {
            this.isVerifyPolicy = isVerifyPolicy;
        }

        public Boolean getIsVerifyPolicy() {
            return isVerifyPolicy;
        }

        public void setIsBestCabin(Boolean isBestCabin) {
            this.isBestCabin = isBestCabin;
        }

        public Boolean getIsBestCabin() {
            return isBestCabin;
        }

    }

    public class PnrMsg {

        private String pnrCode;
        private String pnrOffice;
        private String pnrDetail;

        public void setPnrCode(String pnrCode) {
            this.pnrCode = pnrCode;
        }

        public String getPnrCode() {
            return pnrCode;
        }

        public void setPnrOffice(String pnrOffice) {
            this.pnrOffice = pnrOffice;
        }

        public String getPnrOffice() {
            return pnrOffice;
        }

        public void setPnrDetail(String pnrDetail) {
            this.pnrDetail = pnrDetail;
        }

        public String getPnrDetail() {
            return pnrDetail;
        }

    }

}
