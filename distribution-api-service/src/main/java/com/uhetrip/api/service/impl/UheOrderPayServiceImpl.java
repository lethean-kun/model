package com.uhetrip.api.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.uhetrip.api.dto.UheOrderPayReq;
import com.uhetrip.api.dto.UheOrderPayRsp;
import com.uhetrip.api.dto.UheOrderPayRsp.PayInfo;
import com.uhetrip.api.dto.common.ErrorInfo;
import com.uhetrip.api.dto.common.UheReq;
import com.uhetrip.api.dto.enums.UheApiStatus;
import com.uhetrip.api.service.AbstractService;
import com.uhetrip.bean.accounting.LimitPrerequireGetReq;
import com.uhetrip.bean.accounting.LimitPrerequireGetResp;
import com.uhetrip.bean.accounting.LimitPrerequireGetResp.PaymentInfo;
import com.uhetrip.bean.accounting.LimitRequireReq;
import com.uhetrip.bean.accounting.LimitRequireResp;
import com.uhetrip.bean.dbservice.OrderPayReq;
import com.uhetrip.bean.dbservice.OrderPayResp;
import com.uhetrip.bean.dbservice.OrderStatusUpdateReq;
import com.uhetrip.bean.dbservice.OrderStatusUpdateResp;
import com.uhetrip.bean.enums.UserType;
import com.uhetrip.bean.order.paychecking.PayCheckingReq;
import com.uhetrip.bean.order.paychecking.PayCheckingResp;
import com.uhetrip.bean.order.ticketing.TicketingReq;
import com.uhetrip.bean.order.ticketing.TicketingResp;

@Service("uheOrderPayService")
public class UheOrderPayServiceImpl extends AbstractService<UheOrderPayReq, UheOrderPayRsp> {
    private static final Logger logger = LoggerFactory.getLogger(UheOrderPayServiceImpl.class);

    @Override
    public String getAction(UheReq req) {
        return "order_pay";
    }
    
    @Override
    public UheOrderPayRsp process(UheOrderPayReq q) throws Exception {
        UheOrderPayRsp resp = new UheOrderPayRsp();
        resp.setIsSuccess(true);
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
        PayCheckingReq payCheckingReq = new PayCheckingReq();
        payCheckingReq.setUserId(q.getAgentId());
        payCheckingReq.setUserType(UserType.BUYSER.value());
        payCheckingReq.setOrderId(q.getParam().getOrderNo());
        PayCheckingResp payCheckingResp = uheService.payChecking(payCheckingReq);
        if (payCheckingResp.getStatus() != UheApiStatus.SUCCESS.getCode()) {
            ErrorInfo errorInfo = new ErrorInfo();
            errorInfo.setErrorCode(UheApiStatus.SYSTEM_ERROR.name());
            errorInfo.setErrorMsg(payCheckingResp.getMessage());
            resp.setErrorInfo(errorInfo);
            resp.setIsSuccess(false);
            return resp;
        } else {
            LimitPrerequireGetReq limitPrerequireGetReq = new LimitPrerequireGetReq();
            limitPrerequireGetReq.setUserId(q.getAgentId());
            limitPrerequireGetReq.setUserType(UserType.BUYSER.value());
            limitPrerequireGetReq.setOrderId(q.getParam().getOrderNo());
            LimitPrerequireGetResp limitPrerequireGetResp = uheService.limitPrerequireGet(limitPrerequireGetReq);
            LimitRequireReq limitRequireReq = new LimitRequireReq();
            limitRequireReq.setUserId(q.getAgentId());
            limitRequireReq.setUsername(q.getAgent());
            limitRequireReq.setUserType(UserType.BUYSER.value());
            limitRequireReq.setOrderId(q.getParam().getOrderNo());
            limitRequireReq.setPayType(q.getParam().getPayType());
            limitRequireReq.setAccountType(4);
            for(PaymentInfo paymentInfo : limitPrerequireGetResp.getPaymentInfos()){
                if(q.getParam().getPayType() == paymentInfo.getPayType()){
                    limitRequireReq.setPrice(paymentInfo.getPaymentPrice());
                }
            }
            LimitRequireResp limitRequireResp = uheService.limitRequire(limitRequireReq);
            if (limitRequireResp.getStatus() == UheApiStatus.SUCCESS.getCode()) {
                PayInfo payInfo = resp.new PayInfo();
                payInfo.setOrderNo(limitRequireResp.getOrderId());
                payInfo.setTotalPrice(limitRequireResp.getPrice());
                payInfo.setTradeNo(limitRequireResp.getBillingId());
                payInfo.setStatus(limitRequireResp.getStatus());
                resp.setPayInfo(payInfo);
                OrderPayReq orderPayReq = new OrderPayReq();
                orderPayReq.setUserId(q.getAgentId());
                orderPayReq.setUserType(UserType.BUYSER.value());
                orderPayReq.setOrderId(limitRequireResp.getOrderId());
                orderPayReq.setPaymentTime(limitRequireResp.getPaymentTime());
                orderPayReq.setPaymentType(limitRequireResp.getPayType());
                orderPayReq.setPaymentVoucher(limitRequireResp.getBillingId());
                OrderPayResp orderPayResp = uheService.orderPay(orderPayReq);
                TicketingReq ticketingReq = new TicketingReq();
                ticketingReq.setUserId(q.getAgentId());
                ticketingReq.setUserType(UserType.BUYSER.value());
                ticketingReq.setOrderId(limitRequireResp.getOrderId());
                TicketingResp ticketingResp = uheService.ticketing(ticketingReq);
                OrderStatusUpdateReq orderStatusUpdateReq = new OrderStatusUpdateReq();
                orderStatusUpdateReq.setUserId(q.getAgentId());
                orderStatusUpdateReq.setUserType(UserType.BUYSER.value());
                orderStatusUpdateReq.setOrderId(limitRequireResp.getOrderId());
                orderStatusUpdateReq.setMainStatus(3);
                orderStatusUpdateReq.setSubStatus(1);
                orderStatusUpdateReq.setReturnDetails(false);
                OrderStatusUpdateResp orderStatusUpdateResp = uheService.orderStatusUpdate(orderStatusUpdateReq);
                String ticketMessage = "设置支付状态 ：" + orderPayResp.getStatus() + "," + "设置支付信息：" + orderPayResp
                    .getMessage() + ";";
                ticketMessage += "通知出票状态 ：" + ticketingResp.getStatus() + "," + "通知出票信息：" + ticketingResp.getMessage()
                    + ";";
                ticketMessage += "更新订单状态状态 ：" + orderStatusUpdateResp.getStatus() + "," + "更新订单状态信息："
                    + orderStatusUpdateResp.getMessage() + ";";
                logger.info(ticketMessage);
            } else {
                ErrorInfo errorInfo = new ErrorInfo();
                if (limitRequireResp.getStatus() == 1005) {
                    errorInfo.setErrorCode(UheApiStatus.TRADE_TOTALFEE_NOT_ENOUGH.name());
                } else {
                    errorInfo.setErrorCode(UheApiStatus.SYSTEM_ERROR.name());
                }
                errorInfo.setErrorMsg(limitPrerequireGetResp.getMessage());
                resp.setErrorInfo(errorInfo);
                resp.setIsSuccess(false);
                return resp;
            
            }
        }
        return resp;
    }

    private String check(UheOrderPayReq q) {
        if (StringUtils.isEmpty(q.getSessionid())) {
            return "sessionid must be not null";
        }
        if (q.getParam() != null) {
            if (StringUtils.isEmpty(q.getParam().getData())) {
                return "data must be not null";
            }
            if (StringUtils.isEmpty(q.getParam().getOrderNo())) {
                return "orderNo must be not null";
            }
            if (q.getParam().getPayType() == null) {
                return "payType must be not null";
            }
            if (q.getParam().getTotalPrice() == null) {
                return "totalPrice must be not null";
            }

        } else {
            return "param must be not null";
        }

        return null;
    }
}
