package com.uhetrip.api.service.impl;

import com.uhetrip.api.dto.UheOrderListReq;
import com.uhetrip.api.dto.UheOrderListResp;
import com.uhetrip.api.dto.common.*;
import com.uhetrip.api.dto.enums.UheApiStatus;
import com.uhetrip.api.service.AbstractService;
import com.uhetrip.bean.common.BaggageInfo;
import com.uhetrip.bean.common.Order;
import com.uhetrip.bean.common.Passenger;
import com.uhetrip.bean.common.Segment;
import com.uhetrip.bean.dbservice.OrderListReq;
import com.uhetrip.bean.dbservice.OrderListResp;
import com.uhetrip.bean.enums.UserType;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("orderListService")
public class OrderListServiceImpl extends AbstractService<UheOrderListReq, UheOrderListResp> {

    @Override
    public String getAction(UheReq req) {
        return "order_list";
    }
    
    @Override
    public UheOrderListResp process(UheOrderListReq q) throws Exception {

        UheOrderListResp resp = new UheOrderListResp();
        resp.setIsSuccess(true);
        resp.setAction(q.getAction());
        resp.setAgent(q.getAgent());

        OrderListReq orderListReq = new OrderListReq();

        orderListReq.setUserType(UserType.BUYSER.value());
        orderListReq.setUserId(q.getAgentId());
        orderListReq.setUsername(q.getAgent());

        orderListReq.setFlightNumber(q.getParam().getFlightNo());
        orderListReq.setBookDateEnd(q.getParam().getEndDate());
        orderListReq.setBookDateStart(q.getParam().getStartDate());
        orderListReq.setCarrier(q.getParam().getAirlineCode());
        orderListReq.setOrderId(q.getParam().getOrderID());
        orderListReq.setPnr(q.getParam().getPnrCode());
        orderListReq.setTicketNo(q.getParam().getTicketNo());
        orderListReq.setPassengerName(q.getParam().getPsgName());
        orderListReq.setMainStatus(q.getParam().getStatus());

        orderListReq.setPageIndex(q.getPageIndex());
        orderListReq.setPageSize(q.getPageSize());

        OrderListResp orderListResp = uheService.dbServiceOrderList(orderListReq);

        if (orderListResp.getStatus() != UheApiStatus.SUCCESS.getCode()) {
            resp.setIsSuccess(false);
            ErrorInfo errorInfo = new ErrorInfo();
            errorInfo.setErrorCode(UheApiStatus.SYSTEM_ERROR.name());
            errorInfo.setErrorMsg(orderListResp.getMessage());
            resp.setErrorInfo(errorInfo);
            return resp;
        }

        List<Order> orders = orderListResp.getOrders();

        List<OrderInfo> orderInfoList = new ArrayList<>();

        for (Order order : orders) {

            OrderInfo orderInfo = new OrderInfo();

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
                    if (!map.containsKey(String.valueOf(baggageInfo.getItineraryIndex()) + "-" + String.valueOf(
                        baggageInfo.getSegmentIndex()))) {
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
                BaggageInfo baggageInfo = map.get(String.valueOf(segment.getItineraryIndex()) + "-" + String.valueOf(
                    segment.getSegmentIndex()));
                if (baggageInfo != null) {
                    luggage.setAdtBaggage(baggageInfo.getAdultBaggage());
                    luggage.setChdBaggage(baggageInfo.getChildBaggage());
                }
                flightDetail.setLuggage(luggage);

                flightDetail.setOperateAirline(segment.getCarrier());
                flightDetails.add(flightDetail);
            }
            orderInfo.setFlightInfo(flightDetails);

            orderInfoList.add(orderInfo);
        }
        resp.setOrderinfo(orderInfoList);
        resp.setTotalPage(orderListResp.getTotalPage());
        resp.setTotalRow(orderListResp.getTotalRow());

        return resp;
    }
}
