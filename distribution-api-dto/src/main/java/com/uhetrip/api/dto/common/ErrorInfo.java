package com.uhetrip.api.dto.common;

import com.uhetrip.api.dto.enums.UheApiStatus;

/**
 * 错误信息
 * 
 * @author lihaipeng
 * @Date 2018年7月12日 上午10:36:18
 */
public class ErrorInfo {

    private String errorCode;
    private String errorMsg;

    public ErrorInfo() {
    }

    public ErrorInfo(UheApiStatus uheApiStatus) {
        this.errorCode = uheApiStatus.name();
        this.errorMsg = uheApiStatus.getMessage();
    }

    public ErrorInfo(String errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

}
