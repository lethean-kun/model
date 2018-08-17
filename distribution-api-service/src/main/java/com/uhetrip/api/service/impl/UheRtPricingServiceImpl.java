package com.uhetrip.api.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.uhetrip.api.dto.UheRtPricingReq;
import com.uhetrip.api.dto.UheRtPricingRsp;
import com.uhetrip.api.dto.UheRtPricingRsp.FlightGroup;
import com.uhetrip.api.dto.UheRtPricingRsp.PriceInfo;
import com.uhetrip.api.dto.UheRtPricingRsp.RecommendationInfo;
import com.uhetrip.api.dto.UheRtPricingRsp.SolutionInfo;
import com.uhetrip.api.dto.common.ErrorInfo;
import com.uhetrip.api.dto.common.FlightDetail;
import com.uhetrip.api.dto.common.Luggage;
import com.uhetrip.api.dto.common.PsgPriceInfo;
import com.uhetrip.api.dto.common.UheReq;
import com.uhetrip.api.dto.enums.UheApiStatus;
import com.uhetrip.api.service.AbstractService;
import com.uhetrip.bean.commission.matching.common.CommissionInfo;
import com.uhetrip.bean.common.BaggageInfo;
import com.uhetrip.bean.common.Rule;
import com.uhetrip.bean.common.Segment;
import com.uhetrip.bean.enums.UserType;
import com.uhetrip.bean.order.bestbuy.BestbuyReq;
import com.uhetrip.bean.order.bestbuy.BestbuyResp;
import com.uhetrip.bean.order.bestbuy.SameCabin;
import com.uhetrip.util.constant.Constant;

@Service("uheRtPricingService")
public class UheRtPricingServiceImpl extends AbstractService<UheRtPricingReq, UheRtPricingRsp> {

    @Override
    public String getAction(UheReq req) {
        return "rt_pricing";
    }

    @Override
    public UheRtPricingRsp process(UheRtPricingReq q) throws Exception {
        UheRtPricingRsp resp = new UheRtPricingRsp();
        resp.setAction(q.getAction());
        resp.setAgent(q.getAgent());
        String message = this.check(q);
        if (message != null) {
            ErrorInfo errorInfo = new ErrorInfo();
            errorInfo.setErrorCode(UheApiStatus.ILLEGAL_ARGUMENT.name());
            errorInfo.setErrorMsg(message);
            resp.setErrorInfo(errorInfo);
            resp.setIsSuccess(false);
            return resp;
        }
        BestbuyReq bestbuyReq = new BestbuyReq();
        bestbuyReq.setRTContent(q.getPnrMsg().getPnrDetail());
        bestbuyReq.setUserId(q.getAgentId());
        bestbuyReq.setUsername(q.getAgent());
        bestbuyReq.setUserType(UserType.BUYSER.value());
        bestbuyReq.setSourceType(Constant.SOURCETYPE_API);
        if (q.getControllerParam().getIsBestCabin() == true) {
            bestbuyReq.setOnlySameCabin(false);
        } else {
            bestbuyReq.setOnlySameCabin(true);
        }

        BestbuyResp bestbuyResp = uheService.bestbuy(bestbuyReq);
        if (bestbuyResp.getStatus() != UheApiStatus.SUCCESS.getCode()) {
            ErrorInfo errorInfo = new ErrorInfo();
            errorInfo.setErrorCode(UheApiStatus.SYSTEM_ERROR.name());
            errorInfo.setErrorMsg(bestbuyResp.getMessage());
            resp.setErrorInfo(errorInfo);
            resp.setIsSuccess(false);
            return resp;
        } else {
            resp.setIsSuccess(true);
            this.convertMessage(q, bestbuyResp, resp);
            return resp;
        }
    }

    private void convertMessage(UheRtPricingReq q, BestbuyResp bestbuyResp, UheRtPricingRsp resp) {
        SolutionInfo solutionInfo = resp.new SolutionInfo();
        List<FlightGroup> flightGroupList = new ArrayList<>();
        FlightGroup sameCabinFlightGroup = resp.new FlightGroup();
        sameCabinFlightGroup.setRefId("1");
        if (bestbuyResp.getSameCabin().getStatus() == UheApiStatus.SUCCESS.getCode()) {
            sameCabinFlightGroup.setFlightInfo(this.convertFlightDetailList(bestbuyResp.getSameCabin(), resp));
        } else {
            sameCabinFlightGroup.setFlightInfo(new ArrayList<>());
        }
        flightGroupList.add(sameCabinFlightGroup);
        if (q.getControllerParam().getIsBestCabin() == true) {
            FlightGroup lowestPriceFlightGroup = resp.new FlightGroup();
            lowestPriceFlightGroup.setRefId("2");
            if (bestbuyResp.getLowestPrice().getStatus() == UheApiStatus.SUCCESS.getCode()) {
                lowestPriceFlightGroup.setFlightInfo(this.convertFlightDetailList(bestbuyResp.getLowestPrice(), resp));
            } else {
                lowestPriceFlightGroup.setFlightInfo(new ArrayList<>());
            }
            flightGroupList.add(lowestPriceFlightGroup);
        }
        solutionInfo.setFlightGroup(flightGroupList);
        RecommendationInfo recommendationInfo = resp.new RecommendationInfo();
        recommendationInfo.setPriceInfo(this.convertPriceInfoList(bestbuyResp, resp, q));
        solutionInfo.setRecommendationInfo(recommendationInfo);
        resp.setSolutionInfo(solutionInfo);

    }

    private List<PriceInfo> convertPriceInfoList(BestbuyResp bestbuyResp, UheRtPricingRsp resp, UheRtPricingReq q) {
        List<PriceInfo> priceInfoList = new ArrayList<>();
        if (bestbuyResp.getSameCabin().getStatus() == UheApiStatus.SUCCESS.getCode()) {
            for (CommissionInfo commissionInfo : bestbuyResp.getSameCabin().getRouting().getCommissions()) {
                if (this.filter(q, commissionInfo)) {
                    priceInfoList.add(this.convertPriceInfo(commissionInfo, resp, "1", bestbuyResp.getSameCabin().getRouting().getValidatingCarrier()));
                }
            }
        }
        if (q.getControllerParam().getIsBestCabin() == true) {
            if (bestbuyResp.getLowestPrice().getStatus() == UheApiStatus.SUCCESS.getCode()) {
                for (CommissionInfo commissionInfo : bestbuyResp.getLowestPrice().getRouting().getCommissions()) {
                    if (this.filter(q, commissionInfo)) {
                        priceInfoList.add(this.convertPriceInfo(commissionInfo, resp, "2", bestbuyResp.getLowestPrice().getRouting().getValidatingCarrier()));
                    }
                }
            }
        }
        return priceInfoList;
    }

    private boolean filter(UheRtPricingReq q, CommissionInfo commissionInfo) {
        if (q.getControllerParam().getIsChangePnr() == true) {
            if (!("2".equals(commissionInfo.getReplaceCodeType()) || "4".equals(commissionInfo.getReplaceCodeType()))) {
                return false;
            }
        }
        if (q.getControllerParam().getIsOverseaEt() == true) {
            if (!("GDS".equals(commissionInfo.getTicketType()))) {
                return false;
            }
        }
        if (q.getControllerParam().getIsVerifyPolicy() == true) {
            if (!(commissionInfo.getVerifyType() != null && commissionInfo.getVerifyType() > 0)) {
                return false;
            }
        }
        return true;
    }

    private PriceInfo convertPriceInfo(CommissionInfo commissionInfo, UheRtPricingRsp resp, String flightRefId, String validateCarrier) {
        PriceInfo priceInfo = resp.new PriceInfo();
        priceInfo.setAvailNum(commissionInfo.getMaxSeats());
        priceInfo.setData(commissionInfo.getData());
        priceInfo.setFlightRefId(flightRefId);
        if ("2".equals(flightRefId)) {
            priceInfo.setIsBestCabin(true);
        } else {
            priceInfo.setIsBestCabin(false);

        }
        if ("2".equals(commissionInfo.getReplaceCodeType()) || "4".equals(commissionInfo.getReplaceCodeType())) {
            priceInfo.setIsChangePnr(true);
        } else {
            priceInfo.setIsChangePnr(false);
        }
        if ("GDS".equals(commissionInfo.getTicketType())) {
            priceInfo.setIsOverseaEt(true);
        } else {
            priceInfo.setIsOverseaEt(false);
        }
        if (commissionInfo.getVerifyType() != null && commissionInfo.getVerifyType() > 0) {
            priceInfo.setIsVerifyPolicy(true);
        } else {
            priceInfo.setIsVerifyPolicy(false);
        }
        priceInfo.setPenaltyRule(commissionInfo.getRule().getPenalty16());
        priceInfo.setSessionid(commissionInfo.getSessionId());
        priceInfo.setValidateCarrier(validateCarrier);
        List<PsgPriceInfo> psgPriceInfoList = this.convertPsgPriceInfo(commissionInfo, resp);
        priceInfo.setPsgPriceInfo(psgPriceInfoList);
        return priceInfo;

    }

    private List<PsgPriceInfo> convertPsgPriceInfo(CommissionInfo commissionInfo, UheRtPricingRsp resp) {
        List<PsgPriceInfo> psgPriceInfoList = new ArrayList<>();
        if (commissionInfo.getChild() > 0) {
            PsgPriceInfo childPsgPriceInfo = new PsgPriceInfo();
            childPsgPriceInfo.setAddMoney(commissionInfo.getChdCommissionFee());
            childPsgPriceInfo.setBaseFare(commissionInfo.getCommissionChildPrice());
            childPsgPriceInfo.setDiscount(commissionInfo.getChdAgencyFee());
            childPsgPriceInfo.setPsgType(1);
            childPsgPriceInfo.setTax(commissionInfo.getCommissionChildTax());
            childPsgPriceInfo.setTicketFee(commissionInfo.getTicketingFee());
            childPsgPriceInfo.setTotalFare(commissionInfo.getCommissionChildPrice() + commissionInfo
                .getCommissionChildTax());
            psgPriceInfoList.add(childPsgPriceInfo);
        }
        PsgPriceInfo adultPsgPriceInfo = new PsgPriceInfo();
        adultPsgPriceInfo.setAddMoney(commissionInfo.getAdtCommissionFee());
        adultPsgPriceInfo.setBaseFare(commissionInfo.getCommissionAdultPrice());
        adultPsgPriceInfo.setDiscount(commissionInfo.getAdtAgencyFee());
        adultPsgPriceInfo.setPsgType(0);
        adultPsgPriceInfo.setTax(commissionInfo.getCommissionAdultTax());
        adultPsgPriceInfo.setTicketFee(commissionInfo.getTicketingFee());
        adultPsgPriceInfo.setTotalFare(commissionInfo.getCommissionAdultPrice() + commissionInfo
            .getCommissionAdultTax());
        psgPriceInfoList.add(adultPsgPriceInfo);
        return psgPriceInfoList;
    }

    private String check(UheRtPricingReq q) {
        if (q.getControllerParam() != null) {
            if (q.getControllerParam().getIsBestCabin() == null) {
                return "isBestCabin must be not null";
            }
            if (q.getControllerParam().getIsChangePnr() == null) {
                return "isChangePnr must be not null";
            }
            if (q.getControllerParam().getIsOverseaEt() == null) {
                return "isOverseaEt must be not null";
            }
            if (q.getControllerParam().getIsVerifyPolicy() == null) {
                return "isVerifyPolicy must be not null";
            }
        } else {
            return "controllerParam must be not null";
        }
        if (q.getPnrMsg() != null) {
            if (StringUtils.isEmpty(q.getPnrMsg().getPnrDetail())) {
                return "pnrDetail must be not null";
            }
        } else {
            return "pnrMsg must be not null";
        }
        return null;
    }

    private List<FlightDetail> convertFlightDetailList(SameCabin sameCabin, UheRtPricingRsp resp) {
        List<FlightDetail> flightDetailList = new ArrayList<>();
        for (Segment segment : sameCabin.getRouting().getFromSegments()) {
            FlightDetail flightDetail = this.convertFlightDetail(segment, resp, sameCabin.getRouting().getRule());
            flightDetailList.add(flightDetail);
        }
        for (Segment segment : sameCabin.getRouting().getRetSegments()) {
            FlightDetail flightDetail = this.convertFlightDetail(segment, resp, sameCabin.getRouting().getRule());
            flightDetailList.add(flightDetail);
        }
        return flightDetailList;
    }

    private FlightDetail convertFlightDetail(Segment segment, UheRtPricingRsp resp, Rule rule) {
        FlightDetail flightDetail = new FlightDetail();
        flightDetail.setArrAirport(segment.getArrAirport());
        flightDetail.setArrDateTime(segment.getArrDatetime());
        flightDetail.setArrTerminal(segment.getArrTerminal());
        flightDetail.setCabinCode(segment.getCabin());
        flightDetail.setCabinGrade(segment.getCabinGrade());
        flightDetail.setDepAirport(segment.getDepAirport());
        flightDetail.setDepDateTime(segment.getDepDatetime());
        flightDetail.setDepTerminal(segment.getDepTerminal());
        flightDetail.setEquipType(segment.getAircraftCode());
        flightDetail.setFarebasis(segment.getFarebasis());
        flightDetail.setFlightNo(segment.getFlightNumber());
        flightDetail.setIsCodeShare(segment.getCodeShare());
        flightDetail.setItineraryIndex(segment.getItineraryIndex());
        flightDetail.setOperateAirline(segment.getCarrier());
        flightDetail.setSegmentIndex(segment.getSegmentIndex());
        Luggage luggage = this.convertLuggage(segment.getItineraryIndex(), segment.getSegmentIndex(), rule
            .getBaggageInfoList(), resp);
        flightDetail.setLuggage(luggage);
        return flightDetail;
    }

    private Luggage convertLuggage(Integer itineraryIndex, Integer segmentIndex, List<BaggageInfo> baggageInfoList,
        UheRtPricingRsp resp) {
        Luggage luggage = new Luggage();
        if (baggageInfoList != null) {
            for (BaggageInfo baggageInfo : baggageInfoList) {
                if (itineraryIndex == baggageInfo.getItineraryIndex() && segmentIndex == baggageInfo
                    .getSegmentIndex()) {
                    luggage.setAdtBaggage(baggageInfo.getAdultBaggage());
                    luggage.setChdBaggage(baggageInfo.getChildBaggage());
                }
            }
        }
        return luggage;
    }

}
