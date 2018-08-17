package com.uhetrip.api.dto;

import java.util.List;

import com.uhetrip.api.dto.common.ErrorInfo;
import com.uhetrip.api.dto.common.FlightDetail;
import com.uhetrip.api.dto.common.PsgPriceInfo;
import com.uhetrip.api.dto.common.UheRsp;

public class UheRtPricingRsp extends UheRsp {
    private ErrorInfo errorInfo;
    private SolutionInfo solutionInfo;

    public ErrorInfo getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(ErrorInfo errorInfo) {
        this.errorInfo = errorInfo;
    }

    public SolutionInfo getSolutionInfo() {
        return solutionInfo;
    }

    public void setSolutionInfo(SolutionInfo solutionInfo) {
        this.solutionInfo = solutionInfo;
    }

    public class FlightGroup {

        private String refId;
        private List<FlightDetail> flightInfo;

        public void setRefId(String refId) {
            this.refId = refId;
        }

        public String getRefId() {
            return refId;
        }

        public List<FlightDetail> getFlightInfo() {
            return flightInfo;
        }

        public void setFlightInfo(List<FlightDetail> flightInfo) {
            this.flightInfo = flightInfo;
        }

    }

    public class SolutionInfo {

        private List<FlightGroup> flightGroup;
        private RecommendationInfo recommendationInfo;

        public void setFlightGroup(List<FlightGroup> flightGroup) {
            this.flightGroup = flightGroup;
        }

        public List<FlightGroup> getFlightGroup() {
            return flightGroup;
        }

        public RecommendationInfo getRecommendationInfo() {
            return recommendationInfo;
        }

        public void setRecommendationInfo(RecommendationInfo recommendationInfo) {
            this.recommendationInfo = recommendationInfo;
        }

    }

    public class PriceInfo {

        private String data;
        private String flightRefId;
        private String sessionid;
        private String validateCarrier;
        private Boolean isChangePnr;
        private Boolean isOverseaEt;
        private Boolean isVerifyPolicy;
        private Boolean isBestCabin;
        private String penaltyRule;
        private Integer availNum;
        private List<PsgPriceInfo> psgPriceInfo;

        public void setData(String data) {
            this.data = data;
        }

        public String getData() {
            return data;
        }

        public void setFlightRefId(String flightRefId) {
            this.flightRefId = flightRefId;
        }

        public String getFlightRefId() {
            return flightRefId;
        }

        public void setSessionid(String sessionid) {
            this.sessionid = sessionid;
        }

        public String getSessionid() {
            return sessionid;
        }

        public void setValidateCarrier(String validateCarrier) {
            this.validateCarrier = validateCarrier;
        }

        public String getValidateCarrier() {
            return validateCarrier;
        }

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

        public void setPenaltyRule(String penaltyRule) {
            this.penaltyRule = penaltyRule;
        }

        public String getPenaltyRule() {
            return penaltyRule;
        }

        public void setAvailNum(Integer availNum) {
            this.availNum = availNum;
        }

        public Integer getAvailNum() {
            return availNum;
        }

        public void setPsgPriceInfo(List<PsgPriceInfo> psgPriceInfo) {
            this.psgPriceInfo = psgPriceInfo;
        }

        public List<PsgPriceInfo> getPsgPriceInfo() {
            return psgPriceInfo;
        }

    }

    public class RecommendationInfo {

        private List<PriceInfo> priceInfo;

        public void setPriceInfo(List<PriceInfo> priceInfo) {
            this.priceInfo = priceInfo;
        }

        public List<PriceInfo> getPriceInfo() {
            return priceInfo;
        }

    }

}
