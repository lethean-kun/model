package com.uhetrip.api.dto.common;

/**
 * 优合采购商API响应公共信息
 * 
 * @author lihaipeng
 * @Date 2018年7月12日 上午10:37:17
 */
public class UheRsp {

    private String action;
    private String agent;
    private Boolean isSuccess;
    private ErrorInfo errorInfo;
    private String sign;

    private long spendms;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public Boolean getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(Boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public ErrorInfo getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(ErrorInfo errorInfo) {
        this.errorInfo = errorInfo;
    }

    public long getSpendms() {
        return spendms;
    }

    public void setSpendms(long spendms) {
        this.spendms = spendms;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

}
