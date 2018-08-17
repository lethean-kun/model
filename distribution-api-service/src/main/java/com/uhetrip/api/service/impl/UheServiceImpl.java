package com.uhetrip.api.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.uhetrip.api.config.AppConfig;
import com.uhetrip.api.service.IUheService;
import com.uhetrip.bean.accounting.LimitPrerequireGetReq;
import com.uhetrip.bean.accounting.LimitPrerequireGetResp;
import com.uhetrip.bean.accounting.LimitRequireReq;
import com.uhetrip.bean.accounting.LimitRequireResp;
import com.uhetrip.bean.auth.GetParentUserReq;
import com.uhetrip.bean.auth.GetParentUserResp;
import com.uhetrip.bean.base.BaseResp;
import com.uhetrip.bean.dbservice.OrderDetailReq;
import com.uhetrip.bean.dbservice.OrderDetailResp;
import com.uhetrip.bean.dbservice.OrderListReq;
import com.uhetrip.bean.dbservice.OrderListResp;
import com.uhetrip.bean.dbservice.OrderPayReq;
import com.uhetrip.bean.dbservice.OrderPayResp;
import com.uhetrip.bean.dbservice.OrderStatusUpdateReq;
import com.uhetrip.bean.dbservice.OrderStatusUpdateResp;
import com.uhetrip.bean.enums.ApiStatus;
import com.uhetrip.bean.logging.LoggingReq;
import com.uhetrip.bean.logging.LoggingResp;
import com.uhetrip.bean.order.bestbuy.BestbuyReq;
import com.uhetrip.bean.order.bestbuy.BestbuyResp;
import com.uhetrip.bean.order.booking.BookingReq;
import com.uhetrip.bean.order.booking.BookingResp;
import com.uhetrip.bean.order.paychecking.PayCheckingReq;
import com.uhetrip.bean.order.paychecking.PayCheckingResp;
import com.uhetrip.bean.order.ticketing.TicketingReq;
import com.uhetrip.bean.order.ticketing.TicketingResp;
import com.worthytrip.commons.http.client.HttpClient;
import com.worthytrip.commons.http.client.module.HeaderBody;

/**
 * UHE后端调用服务类
 * 
 * @author lihaipeng
 * @Date 2018年7月12日 下午1:14:03
 */
@Service("uheService")
public class UheServiceImpl implements IUheService {

    @Resource
    private AppConfig appConfig;

    private static final Logger logger = LoggerFactory.getLogger(UheServiceImpl.class);

    @Override
    public LoggingResp loggingAdd(LoggingReq req) {
        LoggingResp resp = new LoggingResp();
        BaseResp httpLoggingResp = executeHttpPostWithContentTypeJson(appConfig.getLoggingServiceUrlAdd(), JSON
            .toJSONString(req), "logging-add", false);
        if (!httpLoggingResp.getStatus().equals(ApiStatus.BAD_REQUEST.value())) {
            LoggingResp loggingResp = JSON.parseObject(httpLoggingResp.getMessage(), LoggingResp.class);
            resp = loggingResp;
        } else {
            resp.setStatus(httpLoggingResp.getStatus());
            resp.setMessage(httpLoggingResp.getMessage());
        }
        return resp;
    }

    @Override
    public OrderDetailResp dbServiceOrderDetail(OrderDetailReq req) {
        OrderDetailResp resp = new OrderDetailResp();
        BaseResp httpOrderDetailResp = executeHttpPostWithContentTypeJson(appConfig.getDbServiceUrlOrderDetails(), JSON
            .toJSONString(req), "db-orderDetail", true);
        if (!httpOrderDetailResp.getStatus().equals(ApiStatus.BAD_REQUEST.value())) {
            OrderDetailResp orderDetailResp = JSON.parseObject(httpOrderDetailResp.getMessage(), OrderDetailResp.class);
            resp = orderDetailResp;
        } else {
            resp.setStatus(httpOrderDetailResp.getStatus());
            resp.setMessage(httpOrderDetailResp.getMessage());
        }
        return resp;
    }

    private static final Header[] HEADERS = { new BasicHeader("Content-Type", "application/json") };

    private BaseResp executeHttpPostWithContentTypeJson(String url, String data, String name,
        boolean loggerPrintEnabled) {
        BaseResp resp = new BaseResp();
        long spendMs = 0;
        try {
            if (loggerPrintEnabled && logger.isInfoEnabled()) {
                logger.info("UHE-MODULE-REQ-[{}]:{} --> {}", name, data, url);
            }
            long b = System.currentTimeMillis();
            HeaderBody headerBody = HttpClient.executeByPostWithoutGzipWithHeaders(url, data, appConfig
                .getHttpTimeout(), appConfig.getHttpTimeout(), appConfig.getHttpTimeout(), HEADERS);
            String postResult = headerBody.getData();
            spendMs = System.currentTimeMillis() - b;
            if (StringUtils.isEmpty(postResult)) {
                resp.setStatus(ApiStatus.BAD_REQUEST.value());
                resp.setMessage("Bad Request - response is empty");
            } else {
                if (postResult.startsWith(HttpClient.ERROR_INFO)) {
                    resp.setStatus(ApiStatus.BAD_REQUEST.value());
                    resp.setMessage("Bad Request - " + postResult);
                } else {
                    resp = JSON.parseObject(postResult, BaseResp.class);
                    resp.setMessage(postResult);
                }
            }
        } catch (Exception e) {
            resp.setStatus(ApiStatus.BAD_REQUEST.value());
            resp.setMessage("Bad Request - " + e.getMessage());
        }
        if (loggerPrintEnabled && logger.isInfoEnabled()) {
            logger.info("UHE-MODULE-RESP-[{}]:{} --> {} --> {}", name, resp.getMessage(), resp.getSpendms(), spendMs);
        }
        return resp;
    }

    @Override
    public OrderListResp dbServiceOrderList(OrderListReq orderListReq) {
        OrderListResp resp = new OrderListResp();
        BaseResp httpOrderListResp = executeHttpPostWithContentTypeJson(appConfig.getDbServiceUrlOrderList(), JSON
            .toJSONString(orderListReq), "db-orderList", true);
        if (!httpOrderListResp.getStatus().equals(ApiStatus.BAD_REQUEST.value())) {
            OrderListResp orderListResp = JSON.parseObject(httpOrderListResp.getMessage(), OrderListResp.class);
            resp = orderListResp;
        } else {
            resp.setStatus(httpOrderListResp.getStatus());
            resp.setMessage(httpOrderListResp.getMessage());
        }
        return resp;
    }

    @Override
    public BestbuyResp bestbuy(BestbuyReq bestbuyReq) {
        BestbuyResp resp = new BestbuyResp();
        BaseResp httpBestBuyResp = executeHttpPostWithContentTypeJson(appConfig.getOrderServiceUrlBestBuy(), JSON
            .toJSONString(bestbuyReq), "order-bustbuy", true);
        if (!httpBestBuyResp.getStatus().equals(ApiStatus.BAD_REQUEST.value())) {
            BestbuyResp bestbuyResp = JSON.parseObject(httpBestBuyResp.getMessage(), BestbuyResp.class);
            resp = bestbuyResp;
        } else {
            resp.setStatus(httpBestBuyResp.getStatus());
            resp.setMessage(httpBestBuyResp.getMessage());
        }
        return resp;
    }

    @Override
    public BookingResp booking(BookingReq bookingReq) {
        BookingResp resp = new BookingResp();
        BaseResp httpBookingResp = executeHttpPostWithContentTypeJson(appConfig.getOrderServiceUrlBooking(), JSON
            .toJSONString(bookingReq), "order-booking", true);
        if (!httpBookingResp.getStatus().equals(ApiStatus.BAD_REQUEST.value())) {
            BookingResp bookingResp = JSON.parseObject(httpBookingResp.getMessage(), BookingResp.class);
            resp = bookingResp;
        } else {
            resp.setStatus(httpBookingResp.getStatus());
            resp.setMessage(httpBookingResp.getMessage());
        }
        return resp;
    }

    @Override
    public PayCheckingResp payChecking(PayCheckingReq payCheckingReq) {
        PayCheckingResp resp = new PayCheckingResp();
        BaseResp httpPayCheckingResp = executeHttpPostWithContentTypeJson(appConfig.getOrderServiceUrlPayChecking(),
            JSON.toJSONString(payCheckingReq), "order-payChecking", true);
        if (!httpPayCheckingResp.getStatus().equals(ApiStatus.BAD_REQUEST.value())) {
            PayCheckingResp payCheckingResp = JSON.parseObject(httpPayCheckingResp.getMessage(), PayCheckingResp.class);
            resp = payCheckingResp;
        } else {
            resp.setStatus(httpPayCheckingResp.getStatus());
            resp.setMessage(httpPayCheckingResp.getMessage());
        }
        return resp;
    }

    @Override
    public LimitPrerequireGetResp limitPrerequireGet(LimitPrerequireGetReq limitPrerequireGetReq) {
        LimitPrerequireGetResp resp = new LimitPrerequireGetResp();
        BaseResp httpLimitPrerequireGetResp = executeHttpPostWithContentTypeJson(appConfig
            .getAccountingServiceUrlLimitPrerequireGet(), JSON.toJSONString(limitPrerequireGetReq),
            "limitPrerequire-get", true);
        if (!httpLimitPrerequireGetResp.getStatus().equals(ApiStatus.BAD_REQUEST.value())) {
            LimitPrerequireGetResp limitPrerequireGetResp = JSON.parseObject(httpLimitPrerequireGetResp.getMessage(),
                LimitPrerequireGetResp.class);
            resp = limitPrerequireGetResp;
        } else {
            resp.setStatus(httpLimitPrerequireGetResp.getStatus());
            resp.setMessage(httpLimitPrerequireGetResp.getMessage());
        }
        return resp;
    }

    @Override
    public LimitRequireResp limitRequire(LimitRequireReq limitRequireReq) {
        LimitRequireResp resp = new LimitRequireResp();
        BaseResp httpLimitPrerequireResp = executeHttpPostWithContentTypeJson(appConfig
            .getAccountingServiceUrlLimitRequire(), JSON.toJSONString(limitRequireReq), "limitPrerequire", true);
        if (!httpLimitPrerequireResp.getStatus().equals(ApiStatus.BAD_REQUEST.value())) {
            LimitRequireResp limitRequireResp = JSON.parseObject(httpLimitPrerequireResp.getMessage(),
                LimitRequireResp.class);
            resp = limitRequireResp;
        } else {
            resp.setStatus(httpLimitPrerequireResp.getStatus());
            resp.setMessage(httpLimitPrerequireResp.getMessage());
        }
        return resp;
    }

    @Override
    public OrderPayResp orderPay(OrderPayReq orderPayReq) {
        OrderPayResp resp = new OrderPayResp();
        BaseResp httpOrderPayResp = executeHttpPostWithContentTypeJson(appConfig.getDbServiceUrlOrderPay(), JSON
            .toJSONString(orderPayReq), "order-pay", true);
        if (!httpOrderPayResp.getStatus().equals(ApiStatus.BAD_REQUEST.value())) {
            OrderPayResp orderPay = JSON.parseObject(httpOrderPayResp.getMessage(), OrderPayResp.class);
            resp = orderPay;
        } else {
            resp.setStatus(httpOrderPayResp.getStatus());
            resp.setMessage(httpOrderPayResp.getMessage());
        }
        return resp;
    }

    @Override
    public TicketingResp ticketing(TicketingReq ticketingReq) {
        TicketingResp resp = new TicketingResp();
        BaseResp httpTicketingResp = executeHttpPostWithContentTypeJson(appConfig.getTicketingServiceUrlIssue(), JSON
            .toJSONString(ticketingReq), "ticketing-issue", true);
        if (!httpTicketingResp.getStatus().equals(ApiStatus.BAD_REQUEST.value())) {
            TicketingResp ticketingResp = JSON.parseObject(httpTicketingResp.getMessage(), TicketingResp.class);
            resp = ticketingResp;
        } else {
            resp.setStatus(httpTicketingResp.getStatus());
            resp.setMessage(httpTicketingResp.getMessage());
        }
        return resp;
    }

    @Override
    public OrderStatusUpdateResp orderStatusUpdate(OrderStatusUpdateReq orderStatusUpdateReq) {
        OrderStatusUpdateResp resp = new OrderStatusUpdateResp();
        BaseResp httpOrderStatusUpdateResp = executeHttpPostWithContentTypeJson(appConfig
            .getOrderServiceUrlStatusUpdate(), JSON.toJSONString(orderStatusUpdateReq), "orderStatus-update", true);
        if (!httpOrderStatusUpdateResp.getStatus().equals(ApiStatus.BAD_REQUEST.value())) {
            OrderStatusUpdateResp bestbuyResp = JSON.parseObject(httpOrderStatusUpdateResp.getMessage(),
                OrderStatusUpdateResp.class);
            resp = bestbuyResp;
        } else {
            resp.setStatus(httpOrderStatusUpdateResp.getStatus());
            resp.setMessage(httpOrderStatusUpdateResp.getMessage());
        }
        return resp;
    }

    @Override
    public GetParentUserResp getParentUser(GetParentUserReq getParentUserReq) {
        GetParentUserResp resp = new GetParentUserResp();
        BaseResp baseResp = executeHttpPostWithContentTypeJson(appConfig.getAuthServiceUrlGetparentuser(), JSON
            .toJSONString(getParentUserReq), "auth-getParentUser", true);
        if (!baseResp.getStatus().equals(ApiStatus.BAD_REQUEST.value())) {
            resp = JSON.parseObject(baseResp.getMessage(), GetParentUserResp.class);
        } else {
            resp.setStatus(baseResp.getStatus());
            resp.setMessage(baseResp.getMessage());
        }
        return resp;
    }

}
