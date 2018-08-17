package com.uhetrip.api.config;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;

@Component
@Configuration
@EnableApolloConfig({ "UHE.common", "application" })
@RefreshScope
public class AppConfig {
    private static final Logger logger = LoggerFactory.getLogger(AppConfig.class);

    @Value("${global.http.timeout}")
    private Integer httpTimeout;
    @Value("${global.http.max.total}")
    private Integer httpMaxTotal;
    @Value("${global.http.default.max.per.route}")
    private Integer httpDefaultMaxPerRoute;
    @Value("${global.service.db.order.details}")
    private String dbServiceUrlOrderDetails;
    @Value("${global.service.db.order.list}")
    private String dbServiceUrlOrderList;
    @Value("${global.service.db.order.pay}")
    private String dbServiceUrlOrderPay;
    @Value("${global.service.logging.add}")
    private String loggingServiceUrlAdd;
    @Value("${global.service.order.pnrimport.bestbuy}")
    private String orderServiceUrlBestBuy;
    @Value("${global.service.order.pnrimport.booking}")
    private String orderServiceUrlBooking;
    @Value("${global.service.order.payChecking}")
    private String orderServiceUrlPayChecking;
    @Value("${global.service.db.order.status.update}")
    private String orderServiceUrlStatusUpdate;
    @Value("${global.service.accounting.limit.prerequire.get}")
    private String accountingServiceUrlLimitPrerequireGet;
    @Value("${global.service.accounting.limit.require}")
    private String accountingServiceUrlLimitRequire;
    @Value("${global.service.order.ticketing}")
    private String ticketingServiceUrlIssue;
    @Value("${global.service.auth.getparentuser}")
    private String authServiceUrlGetparentuser;
    @Value("${sign.verify.switch:true}")
    private Boolean signVerifySwitch;

    @PostConstruct
    private void initialize() {
        logger.info(this.toString());
    }

    public Integer getHttpTimeout() {
        return httpTimeout;
    }

    public void setHttpTimeout(Integer httpTimeout) {
        this.httpTimeout = httpTimeout;
    }

    public Integer getHttpMaxTotal() {
        return httpMaxTotal;
    }

    public void setHttpMaxTotal(Integer httpMaxTotal) {
        this.httpMaxTotal = httpMaxTotal;
    }

    public Integer getHttpDefaultMaxPerRoute() {
        return httpDefaultMaxPerRoute;
    }

    public void setHttpDefaultMaxPerRoute(Integer httpDefaultMaxPerRoute) {
        this.httpDefaultMaxPerRoute = httpDefaultMaxPerRoute;
    }

    public String getDbServiceUrlOrderDetails() {
        return dbServiceUrlOrderDetails;
    }

    public void setDbServiceUrlOrderDetails(String dbServiceUrlOrderDetails) {
        this.dbServiceUrlOrderDetails = dbServiceUrlOrderDetails;
    }

    public String getDbServiceUrlOrderList() {
        return dbServiceUrlOrderList;
    }

    public void setDbServiceUrlOrderList(String dbServiceUrlOrderList) {
        this.dbServiceUrlOrderList = dbServiceUrlOrderList;
    }

    public String getDbServiceUrlOrderPay() {
        return dbServiceUrlOrderPay;
    }

    public void setDbServiceUrlOrderPay(String dbServiceUrlOrderPay) {
        this.dbServiceUrlOrderPay = dbServiceUrlOrderPay;
    }

    public String getLoggingServiceUrlAdd() {
        return loggingServiceUrlAdd;
    }

    public void setLoggingServiceUrlAdd(String loggingServiceUrlAdd) {
        this.loggingServiceUrlAdd = loggingServiceUrlAdd;
    }

    public String getOrderServiceUrlBestBuy() {
        return orderServiceUrlBestBuy;
    }

    public void setOrderServiceUrlBestBuy(String orderServiceUrlBestBuy) {
        this.orderServiceUrlBestBuy = orderServiceUrlBestBuy;
    }

    public String getOrderServiceUrlBooking() {
        return orderServiceUrlBooking;
    }

    public void setOrderServiceUrlBooking(String orderServiceUrlBooking) {
        this.orderServiceUrlBooking = orderServiceUrlBooking;
    }

    public String getOrderServiceUrlPayChecking() {
        return orderServiceUrlPayChecking;
    }

    public void setOrderServiceUrlPayChecking(String orderServiceUrlPayChecking) {
        this.orderServiceUrlPayChecking = orderServiceUrlPayChecking;
    }

    public String getOrderServiceUrlStatusUpdate() {
        return orderServiceUrlStatusUpdate;
    }

    public void setOrderServiceUrlStatusUpdate(String orderServiceUrlStatusUpdate) {
        this.orderServiceUrlStatusUpdate = orderServiceUrlStatusUpdate;
    }

    public String getAccountingServiceUrlLimitPrerequireGet() {
        return accountingServiceUrlLimitPrerequireGet;
    }

    public void setAccountingServiceUrlLimitPrerequireGet(String accountingServiceUrlLimitPrerequireGet) {
        this.accountingServiceUrlLimitPrerequireGet = accountingServiceUrlLimitPrerequireGet;
    }

    public String getAccountingServiceUrlLimitRequire() {
        return accountingServiceUrlLimitRequire;
    }

    public void setAccountingServiceUrlLimitRequire(String accountingServiceUrlLimitRequire) {
        this.accountingServiceUrlLimitRequire = accountingServiceUrlLimitRequire;
    }

    public String getTicketingServiceUrlIssue() {
        return ticketingServiceUrlIssue;
    }

    public void setTicketingServiceUrlIssue(String ticketingServiceUrlIssue) {
        this.ticketingServiceUrlIssue = ticketingServiceUrlIssue;
    }

    public String getAuthServiceUrlGetparentuser() {
        return authServiceUrlGetparentuser;
    }

    public void setAuthServiceUrlGetparentuser(String authServiceUrlGetparentuser) {
        this.authServiceUrlGetparentuser = authServiceUrlGetparentuser;
    }

    public Boolean getSignVerifySwitch() {
        return signVerifySwitch;
    }

    public void setSignVerifySwitch(Boolean signVerifySwitch) {
        this.signVerifySwitch = signVerifySwitch;
    }

    @Override
    public String toString() {
        return "AppConfig [httpTimeout=" + httpTimeout + ", httpMaxTotal=" + httpMaxTotal + ", httpDefaultMaxPerRoute="
            + httpDefaultMaxPerRoute + ", dbServiceUrlOrderDetails=" + dbServiceUrlOrderDetails
            + ", dbServiceUrlOrderList=" + dbServiceUrlOrderList + ", dbServiceUrlOrderPay=" + dbServiceUrlOrderPay
            + ", loggingServiceUrlAdd=" + loggingServiceUrlAdd + ", orderServiceUrlBestBuy=" + orderServiceUrlBestBuy
            + ", orderServiceUrlBooking=" + orderServiceUrlBooking + ", orderServiceUrlPayChecking="
            + orderServiceUrlPayChecking + ", orderServiceUrlStatusUpdate=" + orderServiceUrlStatusUpdate
            + ", accountingServiceUrlLimitPrerequireGet=" + accountingServiceUrlLimitPrerequireGet
            + ", accountingServiceUrlLimitRequire=" + accountingServiceUrlLimitRequire + ", ticketingServiceUrlIssue="
            + ticketingServiceUrlIssue + ", authServiceUrlGetparentuser=" + authServiceUrlGetparentuser
            + ", signVerifySwitch=" + signVerifySwitch + "]";
    }

}
