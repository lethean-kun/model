package com.uhetrip.api.dto;

import com.uhetrip.api.dto.common.*;

import java.util.List;

/**
 * @author zongzekun
 * @Date 2018年7月13日 上午11:14:09
 */
public class UheOrderDetailsResp extends UheRsp {


    /**
     * orderInfo : {"baseOrderInfo":{"penaltyRule":"","orderNo":"","payType":"","pnrCode":"","payTime":"","createTime":"","TotalPrice":"","status":""}}
     */
    private OrderInfo orderInfo;

    public void setOrderInfo(OrderInfo orderInfo) {
        this.orderInfo = orderInfo;
    }

    public OrderInfo getOrderInfo() {
        return orderInfo;
    }


}
