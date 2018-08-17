package com.uhetrip.api.service.impl;

import com.uhetrip.api.dto.UheOrderDetailsReq;
import com.uhetrip.api.dto.UheOrderDetailsResp;
import com.uhetrip.api.dto.common.*;
import com.uhetrip.api.dto.enums.UheApiStatus;
import com.uhetrip.api.service.AbstractService;
import com.uhetrip.bean.common.*;
import com.uhetrip.bean.dbservice.OrderDetailReq;
import com.uhetrip.bean.dbservice.OrderDetailResp;
import com.uhetrip.bean.enums.UserType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("orderDetailsService")
public class OrderDetailsServiceImpl extends AbstractService<UheOrderDetailsReq, UheOrderDetailsResp> {

    @Override
    public String getAction(UheReq req) {
        return "order_info";
    }

    @Override
    public UheOrderDetailsResp process(UheOrderDetailsReq q) throws Exception {

        UheOrderDetailsResp resp = new UheOrderDetailsResp();
        resp.setIsSuccess(true);
        resp.setAction(q.getAction());
        resp.setAgent(q.getAgent());

        OrderDetailReq orderDetailReq = new OrderDetailReq();
        orderDetailReq.setOrderId(q.getParam().getOrderID());

        orderDetailReq.setUserType(UserType.BUYSER.value());
        orderDetailReq.setUserId(q.getAgentId());
        orderDetailReq.setUsername(q.getAgent());

        OrderDetailResp orderDetailResp = uheService.dbServiceOrderDetail(orderDetailReq);
        if (orderDetailResp.getStatus() != UheApiStatus.SUCCESS.getCode()) {
            resp.setIsSuccess(false);
            ErrorInfo errorInfo = new ErrorInfo();
            errorInfo.setErrorCode(UheApiStatus.SYSTEM_ERROR.name());
            errorInfo.setErrorMsg(orderDetailResp.getMessage());
            resp.setErrorInfo(errorInfo);
            return resp;
        }

        OrderInfo orderInfo = new OrderInfo();

        Order order = orderDetailResp.getOrder();

        List<Passenger> passengers = order.getPassengers();
        List<Segment> segments = order.getSegments();

        BaseOrderInfo baseOrderInfo = new BaseOrderInfo();
        List<PsgInfo> psgInfos = new ArrayList<>();
        List<FlightDetail> flightDetails = new ArrayList<>();
        List<PsgPriceInfo> psgPriceInfos = new ArrayList<>();

        baseOrderInfo.setOrderNo(order.getOrderId());
        baseOrderInfo.setPenaltyRule(order.getRule().getPenalty16());
        baseOrderInfo.setPnrCode(order.getPnr());
        baseOrderInfo.setTotalPrice(order.getPaymentTotalPrice());
        baseOrderInfo.setStatus(order.getMainStatus());
        baseOrderInfo.setPayTime(order.getPaymentTime());
        baseOrderInfo.setPayType(order.getPaymentType());
        baseOrderInfo.setCreateTime(order.getBookingTime());
        baseOrderInfo.setFlightType(order.getTripType());
        orderInfo.setBaseOrderInfo(baseOrderInfo);

        if (order.getAdultNumber() > 0) {
            PsgPriceInfo psgPriceInfo = new PsgPriceInfo();
            psgPriceInfo.setPsgType(0);
            psgPriceInfo.setBaseFare(order.getAdultPrice());
            psgPriceInfo.setTax(order.getAdultTax());
            psgPriceInfo.setTotalFare(order.getAdultTax() + order.getAdultPrice());
            if (order.getPolicyCommissionInfo() != null) {
                psgPriceInfo.setDiscount(order.getPolicyCommissionInfo().getAdtAgencyFee());
                psgPriceInfo.setAddMoney(order.getPolicyCommissionInfo().getAdtCommissionFee());
                psgPriceInfo.setTicketFee(order.getPolicyCommissionInfo().getTicketingFee());
            }

            psgPriceInfos.add(psgPriceInfo);
        }
        if (order.getChildNumber() > 0) {
            PsgPriceInfo psgPriceInfo = new PsgPriceInfo();
            psgPriceInfo.setPsgType(1);
            psgPriceInfo.setBaseFare(order.getChildPrice());
            psgPriceInfo.setTax(order.getChildTax());
            psgPriceInfo.setTotalFare(order.getChildTax() + order.getChildPrice());
            if (order.getPolicyCommissionInfo() != null) {
                psgPriceInfo.setDiscount(order.getPolicyCommissionInfo().getAdtAgencyFee());
                psgPriceInfo.setAddMoney(order.getPolicyCommissionInfo().getAdtCommissionFee());
                psgPriceInfo.setTicketFee(order.getPolicyCommissionInfo().getTicketingFee());
            }

            psgPriceInfos.add(psgPriceInfo);

        }
        orderInfo.setPsgPriceInfo(psgPriceInfos);
        for (Passenger passenger : passengers) {

            PsgInfo psgInfo = new PsgInfo();
            psgInfo.setTicketNo(passenger.getTicketNo());
            psgInfo.setBirthDate(passenger.getBirthday());
            psgInfo.setCountryCode(passenger.getNationality());
            psgInfo.setIdNo(passenger.getCardNum());
            psgInfo.setIdType(passenger.getCardType());
            psgInfo.setIdValidTo(passenger.getCardExpired());
            psgInfo.setPhoneNo("");
            psgInfo.setPsgName(passenger.getName());
            psgInfo.setPsgType(passenger.getAgeType());
            psgInfo.setGender(passenger.getGender());
            psgInfo.setIdIssuePlace(passenger.getCardIssuePlace());
            psgInfos.add(psgInfo);

        }
        orderInfo.setPsgInfo(psgInfos);

        List<BaggageInfo> baggageInfos = order.getRule().getBaggageInfoList();
        Map<String, BaggageInfo> map = new HashMap<>();
        if (baggageInfos != null) {
            for (BaggageInfo baggageInfo : baggageInfos) {
                if (!map.containsKey(String.valueOf(baggageInfo.getItineraryIndex()) + "-" + String.valueOf(baggageInfo
                    .getSegmentIndex()))) {
                    map.put(String.valueOf(baggageInfo.getItineraryIndex()) + "-" + String.valueOf(baggageInfo
                        .getSegmentIndex()), baggageInfo);
                }
            }
        }

        for (Segment segment : segments) {
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
            flightDetail.setSegmentIndex(segment.getSegmentIndex());
            Luggage luggage = new Luggage();
            BaggageInfo baggageInfo = map.get(String.valueOf(segment.getItineraryIndex()) + "-" + String.valueOf(segment
                .getSegmentIndex()));
            if (baggageInfo != null) {
                luggage.setAdtBaggage(baggageInfo.getAdultBaggage());
                luggage.setChdBaggage(baggageInfo.getChildBaggage());
            }

            flightDetail.setLuggage(luggage);

            flightDetail.setOperateAirline(segment.getCarrier());
            flightDetails.add(flightDetail);
        }
        orderInfo.setFlightInfo(flightDetails);

        resp.setOrderInfo(orderInfo);
        return resp;
    }

    @Override
    public String paramCheck(UheOrderDetailsReq q) {
        if (StringUtils.isEmpty(q.getParam().getOrderID())) {
            return "orderId must be not null";
        }
        return null;
    }

}
