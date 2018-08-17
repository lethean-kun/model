package com.uhetrip.api.dto;

import com.uhetrip.api.dto.common.OrderInfo;
import com.uhetrip.api.dto.common.UheRsp;

import java.util.List;

/**
 * @author zongzekun
 * @Date 2018年7月13日 上午11:14:09
 */
public class UheOrderListResp extends UheRsp {


    /**
     * totalRow :
     * totalPage :
     * orderinfo : [{"flightInfo":[{"flightNo":"","depTime":"","airLineCode":"","cabin":"","arrAirport":"","depAirport":"","arrTime":""}],"passengerInfo":[{"idType":"","psgType":"","idValidTo":"","countryCode":"","psgName":"","idNo":"","birthDate":"","phoneNo":""}],"baseOrderInfo":{"penaltyRule":"","orderNo":"","payType":"","pnrCode":"","payTime":"","createTime":"","TotalPrice":"","flightType":"","status":""}}]
     */
    private Integer totalRow;
    private Integer totalPage;
    private List<OrderInfo> orderinfo;

    public Integer getTotalRow() {
        return totalRow;
    }

    public void setTotalRow(Integer totalRow) {
        this.totalRow = totalRow;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public List<OrderInfo> getOrderinfo() {
        return orderinfo;
    }

    public void setOrderinfo(List<OrderInfo> orderinfo) {
        this.orderinfo = orderinfo;
    }
}
