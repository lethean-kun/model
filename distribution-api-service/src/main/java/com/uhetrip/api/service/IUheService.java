package com.uhetrip.api.service;

import com.uhetrip.bean.accounting.LimitPrerequireGetReq;
import com.uhetrip.bean.accounting.LimitRequireReq;
import com.uhetrip.bean.accounting.LimitRequireResp;
import com.uhetrip.bean.auth.GetParentUserReq;
import com.uhetrip.bean.auth.GetParentUserResp;
import com.uhetrip.bean.dbservice.OrderDetailReq;
import com.uhetrip.bean.dbservice.OrderDetailResp;
import com.uhetrip.bean.dbservice.OrderListReq;
import com.uhetrip.bean.dbservice.OrderListResp;
import com.uhetrip.bean.dbservice.OrderPayReq;
import com.uhetrip.bean.dbservice.OrderPayResp;
import com.uhetrip.bean.dbservice.OrderStatusUpdateReq;
import com.uhetrip.bean.dbservice.OrderStatusUpdateResp;
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

public interface IUheService {

    /**
     * 添加日志
     *
     * @param req
     * @return
     */
    LoggingResp loggingAdd(LoggingReq req);

    /**
     * 获取订单明细
     * 
     * @param req
     * @return
     */
    OrderDetailResp dbServiceOrderDetail(OrderDetailReq req);

    /**
     * 获取订单列表
     * 
     * @param orderListReq
     * @return
     */
    OrderListResp dbServiceOrderList(OrderListReq orderListReq);

    /**
     * bestbuy
     * 
     * @param bestbuyReq
     * @return
     */
    BestbuyResp bestbuy(BestbuyReq bestbuyReq);

    /**
     * 生单
     * 
     * @param bookingReq
     * @return
     */
    BookingResp booking(BookingReq bookingReq);

    /**
     * 支付校验
     * 
     * @param payCheckingReq
     * @return
     */
    PayCheckingResp payChecking(PayCheckingReq payCheckingReq);

    /**
     * 额度申请前获取订单金额和支付方式信息
     * 
     * @param limitPrerequireGetReq
     * @return
     */
    com.uhetrip.bean.accounting.LimitPrerequireGetResp limitPrerequireGet(LimitPrerequireGetReq limitPrerequireGetReq);

    /**
     * 支付额度申请
     * 
     * @param limitRequireReq
     * @return
     */
    LimitRequireResp limitRequire(LimitRequireReq limitRequireReq);

    /**
     * 设置支付状态
     * 
     * @param orderPayReq
     * @return
     */
    OrderPayResp orderPay(OrderPayReq orderPayReq);

    /**
     * 通知出票
     * 
     * @param ticketingIssueReq
     * @return
     */
    TicketingResp ticketing(TicketingReq ticketingReq);

    /**
     * 修改订单状态出票中
     * 
     * @param ticketingIssueReq
     * @return
     */
    OrderStatusUpdateResp orderStatusUpdate(OrderStatusUpdateReq orderStatusUpdateReq);

    /**
     * 获取供应商账号信息
     * 
     * @param getParentUserReq
     * @return
     */
    GetParentUserResp getParentUser(GetParentUserReq getParentUserReq);

}
