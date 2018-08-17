package com.uhetrip.api.rest;

import javax.annotation.Resource;

import com.uhetrip.api.dto.*;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.uhetrip.api.dto.common.ErrorInfo;
import com.uhetrip.api.dto.enums.UheApiStatus;
import com.uhetrip.api.service.IService;
import com.uhetrip.bean.exception.BusinessException;

@RestController
@RequestMapping(value = "/${spring.application.name}")
public class RestApi {

    @Resource
    IService<UheRtPricingReq, UheRtPricingRsp> uheRtPricingService;

    @Resource
    IService<UheCeateOrderReq, UheCeateOrderRsp> uheCeateOrderService;

    @Resource
    IService<UheOrderPayReq, UheOrderPayRsp> uheOrderPayService;

    @Resource
    IService<UheOrderDetailsReq, UheOrderDetailsResp> orderDetailsService;

    @Resource
    IService<UheOrderListReq, UheOrderListResp> orderListService;

    @ResponseBody
    @RequestMapping(value = "/rt/pricing/v1", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE }, method = {
        RequestMethod.POST }, consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE })
    public UheRtPricingRsp uheRtPricing(@RequestBody UheRtPricingReq req) {
        long b = System.currentTimeMillis();
        UheRtPricingRsp resp = null;
        try {
            resp = uheRtPricingService.execute(req);
        } catch (BusinessException e) {
            resp = new UheRtPricingRsp();
            resp.setErrorInfo(new ErrorInfo(UheApiStatus.SYSTEM_ERROR));
        } catch (Exception e) {
            resp = new UheRtPricingRsp();
            resp.setErrorInfo(new ErrorInfo(UheApiStatus.SYSTEM_ERROR));
        }
        if (resp == null) {
            resp = new UheRtPricingRsp();
            resp.setErrorInfo(new ErrorInfo(UheApiStatus.SYSTEM_ERROR));
        }
        resp.setSpendms(System.currentTimeMillis() - b);
        return resp;
    }

    @ResponseBody
    @RequestMapping(value = "/create/order/v1", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE }, method = {
        RequestMethod.POST }, consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE })
    public UheCeateOrderRsp uheCeateOrder(@RequestBody UheCeateOrderReq req) {
        long b = System.currentTimeMillis();
        UheCeateOrderRsp resp = null;
        try {
            resp = uheCeateOrderService.execute(req);
        } catch (BusinessException e) {
            resp = new UheCeateOrderRsp();
            resp.setErrorInfo(new ErrorInfo(UheApiStatus.SYSTEM_ERROR));
        } catch (Exception e) {
            resp = new UheCeateOrderRsp();
            resp.setErrorInfo(new ErrorInfo(UheApiStatus.SYSTEM_ERROR));
        }
        if (resp == null) {
            resp = new UheCeateOrderRsp();
            resp.setErrorInfo(new ErrorInfo(UheApiStatus.SYSTEM_ERROR));
        }
        resp.setSpendms(System.currentTimeMillis() - b);
        return resp;
    }

    @ResponseBody
    @RequestMapping(value = "/order/pay/v1", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE }, method = {
        RequestMethod.POST }, consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE })
    public UheOrderPayRsp listRecorders(@RequestBody UheOrderPayReq req) {
        long b = System.currentTimeMillis();
        UheOrderPayRsp resp = null;
        try {
            resp = uheOrderPayService.execute(req);
        } catch (BusinessException e) {
            resp = new UheOrderPayRsp();
            resp.setErrorInfo(new ErrorInfo(UheApiStatus.SYSTEM_ERROR));
        } catch (Exception e) {
            resp = new UheOrderPayRsp();
            resp.setErrorInfo(new ErrorInfo(UheApiStatus.SYSTEM_ERROR));
        }
        if (resp == null) {
            resp = new UheOrderPayRsp();
            resp.setErrorInfo(new ErrorInfo(UheApiStatus.SYSTEM_ERROR));
        }
        resp.setSpendms(System.currentTimeMillis() - b);
        return resp;
    }

    @ResponseBody
    @RequestMapping(value = "/order/info/v1", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE }, method = {
        RequestMethod.POST }, consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE })
    public UheOrderDetailsResp uheOrderDetails(@RequestBody UheOrderDetailsReq req) {
        long b = System.currentTimeMillis();
        UheOrderDetailsResp resp = null;
        try {
            resp = orderDetailsService.execute(req);
        } catch (BusinessException e) {
            resp = new UheOrderDetailsResp();
            resp.setErrorInfo(new ErrorInfo(UheApiStatus.SYSTEM_ERROR));
        } catch (Exception e) {
            resp = new UheOrderDetailsResp();
            resp.setErrorInfo(new ErrorInfo(UheApiStatus.SYSTEM_ERROR));
        }
        if (resp == null) {
            resp = new UheOrderDetailsResp();
            resp.setErrorInfo(new ErrorInfo(UheApiStatus.SYSTEM_ERROR));
        }
        resp.setSpendms(System.currentTimeMillis() - b);
        return resp;
    }

    @ResponseBody
    @RequestMapping(value = "/order/list/v1", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE }, method = {
        RequestMethod.POST }, consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE })
    public UheOrderListResp uheOrderList(@RequestBody UheOrderListReq req) {
        long b = System.currentTimeMillis();
        UheOrderListResp resp = null;
        try {
            resp = orderListService.execute(req);
        } catch (BusinessException e) {
            resp = new UheOrderListResp();
            resp.setErrorInfo(new ErrorInfo(UheApiStatus.SYSTEM_ERROR));
        } catch (Exception e) {
            resp = new UheOrderListResp();
            resp.setErrorInfo(new ErrorInfo(UheApiStatus.SYSTEM_ERROR));
        }
        if (resp == null) {
            resp = new UheOrderListResp();
            resp.setErrorInfo(new ErrorInfo(UheApiStatus.SYSTEM_ERROR));
        }
        resp.setSpendms(System.currentTimeMillis() - b);
        return resp;
    }
}
