package com.uhetrip.api.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.uhetrip.api.dto.UheCeateOrderReq;
import com.uhetrip.api.dto.UheCeateOrderRsp;
import com.uhetrip.api.dto.UheCeateOrderRsp.OrderInfo;
import com.uhetrip.api.dto.common.ErrorInfo;
import com.uhetrip.api.dto.common.PsgInfo;
import com.uhetrip.api.dto.common.UheReq;
import com.uhetrip.api.dto.enums.UheApiStatus;
import com.uhetrip.api.service.AbstractService;
import com.uhetrip.bean.common.Contact;
import com.uhetrip.bean.common.Passenger;
import com.uhetrip.bean.dbservice.OrderDetailReq;
import com.uhetrip.bean.dbservice.OrderDetailResp;
import com.uhetrip.bean.enums.UserType;
import com.uhetrip.bean.order.booking.BookingReq;
import com.uhetrip.bean.order.booking.BookingResp;

@Service("uheCeateOrderService")
public class UheCeateOrderServiceImpl extends AbstractService<UheCeateOrderReq, UheCeateOrderRsp> {

    @Override
    public String getAction(UheReq req) {
        return "create_order";
    }
    
    @Override
    public UheCeateOrderRsp process(UheCeateOrderReq q) throws Exception {
        UheCeateOrderRsp resp = new UheCeateOrderRsp();
        resp.setAction(q.getAction());
        resp.setAgent(q.getAgent());
        resp.setSessionid(q.getSessionid());
        String message = this.check(q);
        if (message != null) {
            ErrorInfo errorInfo = new ErrorInfo();
            errorInfo.setErrorCode(UheApiStatus.ILLEGAL_ARGUMENT.name());
            errorInfo.setErrorMsg(message);
            resp.setErrorInfo(errorInfo);
            resp.setIsSuccess(false);
            return resp;
        }
        BookingReq bookingReq = new BookingReq();
        bookingReq.setUserId(q.getAgentId());
        bookingReq.setUsername(q.getAgent());
        bookingReq.setUserType(UserType.BUYSER.value());
        bookingReq.setData(q.getParam().getData());
        bookingReq.setContact(this.convertContact(q));
        bookingReq.setPassengers(this.convertPassenger(q));
        BookingResp bookingResp = uheService.booking(bookingReq);
        if (bookingResp.getStatus() != UheApiStatus.SUCCESS.getCode()) {
            ErrorInfo errorInfo = new ErrorInfo();
            errorInfo.setErrorCode(UheApiStatus.SYSTEM_ERROR.name());
            errorInfo.setErrorMsg(bookingResp.getMessage());
            resp.setErrorInfo(errorInfo);
            resp.setIsSuccess(false);
            return resp;
        } else {
            resp.setIsSuccess(true);
            OrderDetailReq orderDetailReq = new OrderDetailReq();
            orderDetailReq.setUserId(q.getAgentId());
            orderDetailReq.setUserType(UserType.BUYSER.value());
            orderDetailReq.setOrderId(bookingResp.getOrderId());
            OrderDetailResp orderDetailResp = uheService.dbServiceOrderDetail(orderDetailReq);
            if (orderDetailResp.getStatus() != UheApiStatus.SUCCESS.getCode()) {
                ErrorInfo errorInfo = new ErrorInfo();
                errorInfo.setErrorCode(UheApiStatus.SYSTEM_ERROR.name());
                errorInfo.setErrorMsg(orderDetailResp.getMessage());
                resp.setErrorInfo(errorInfo);
                return resp;
            } else {
                OrderInfo orderInfo = resp.new OrderInfo();
                orderInfo.setOrderId(bookingResp.getOrderId());
                orderInfo.setPnrCode(bookingResp.getPnrCode());
                orderInfo.setData(orderDetailResp.getOrder().getData());
                if ("2".equals(orderDetailResp.getOrder().getPolicyCommissionInfo().getReplaceCodeType()) || "4".equals(
                    orderDetailResp.getOrder().getPolicyCommissionInfo().getReplaceCodeType())) {
                    orderInfo.setIsChangePnr(true);
                } else {
                    orderInfo.setIsChangePnr(false);
                }
                if (orderDetailResp.getOrder().getPolicyCommissionInfo().getVerifyType() != null && orderDetailResp
                    .getOrder().getPolicyCommissionInfo().getVerifyType() > 0) {
                    orderInfo.setIsVerifyPolicy(true);
                } else {
                    orderInfo.setIsVerifyPolicy(false);
                }
                orderInfo.setTotalPrice(orderDetailResp.getOrder().getPaymentTotalPrice());
                resp.setOrderInfo(orderInfo);
            }
        }
        return resp;
    }

    private String check(UheCeateOrderReq q) {
        if (StringUtils.isEmpty(q.getSessionid())) {
            return "sessionid must be not null";
        }
        if (q.getParam() != null) {
            if (StringUtils.isEmpty(q.getParam().getData())) {
                return "data must be not null";
            }
            if (q.getParam().getPsgInfo() != null && q.getParam().getPsgInfo().size() > 0) {
                for(PsgInfo psgInfo : q.getParam().getPsgInfo()){
                    if (psgInfo.getPsgType() == null) {
                        return "psgType must be not null";
                    }
                    if (StringUtils.isEmpty(psgInfo.getCountryCode())) {
                        return "countryCode must be not null";
                    }
                    if (StringUtils.isEmpty(psgInfo.getIdType())) {
                        return "idType must be not null";
                    }
                    if (StringUtils.isEmpty(psgInfo.getIdValidTo())) {
                        return "idValidTo must be not null";
                    }
                    if (StringUtils.isEmpty(psgInfo.getBirthDate())) {
                        return "birthDate must be not null";
                    }
                    if (StringUtils.isEmpty(psgInfo.getPsgName())) {
                        return "psgName must be not null";
                    }
                    if (StringUtils.isEmpty(psgInfo.getIdNo())) {
                        return "idNo must be not null";
                    }
                    if (StringUtils.isEmpty(psgInfo.getIdIssuePlace())) {
                        return "idIssuePlace must be not null";
                    }
                    if (StringUtils.isEmpty(psgInfo.getGender())) {
                        return "gender must be not null";
                    }
                }
            } else {
                return "psgInfo must be not null";
            }
            if (q.getParam().getContactInfo() != null) {
                if (StringUtils.isEmpty(q.getParam().getContactInfo().getEmail())) {
                    return "email must be not null";
                }
                if (StringUtils.isEmpty(q.getParam().getContactInfo().getMobile())) {
                    return "mobile must be not null";
                }
                if (StringUtils.isEmpty(q.getParam().getContactInfo().getName())) {
                    return "name must be not null";
                }

            } else {
                return "contactInfo must be not null";
            }
        } else {
            return "param must be not null";
        }

        return null;
    }

    private List<Passenger> convertPassenger(UheCeateOrderReq q) {
        List<Passenger> passengerList = new ArrayList<>();
        for (PsgInfo psgInfo : q.getParam().getPsgInfo()) {
            Passenger passenger = new Passenger();
            passenger.setAgeType(psgInfo.getPsgType());
            passenger.setNationality(psgInfo.getCountryCode());
            passenger.setCardType(psgInfo.getIdType());
            passenger.setCardExpired(psgInfo.getIdValidTo());
            passenger.setCardNum(psgInfo.getIdNo());
            passenger.setBirthday(psgInfo.getBirthDate());
            passenger.setName(psgInfo.getPsgName());
            passenger.setCardIssuePlace(psgInfo.getIdIssuePlace());
            passenger.setGender(psgInfo.getGender());
            passengerList.add(passenger);
        }
        return passengerList;
    }

    private Contact convertContact(UheCeateOrderReq q) {
        Contact contact = new Contact();
        contact.setAddress(q.getParam().getContactInfo().getAddress());
        contact.setEmail(q.getParam().getContactInfo().getEmail());
        contact.setMobile(q.getParam().getContactInfo().getMobile());
        contact.setName(q.getParam().getContactInfo().getName());
        contact.setPostcode(q.getParam().getContactInfo().getPostcode());
        return contact;
    }
}
