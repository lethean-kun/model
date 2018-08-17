package com.uhetrip.api.dto.enums;

/**
 * API ErrorCode
 */
public enum UheApiStatus {

    /**
     * success
     */
    SUCCESS(0, "success"),
    /**
     * 签名错误
     */
    ILLEGAL_SIGN(1, "签名错误"),
    /**
     * 密钥错误
     */
    ILLEGAL_MD5_KEY(2, "密钥错误"),
    /**
     * 接口名称错误
     */
    ILLEGAL_ACTION(2, "接口名称错误"),
    /**
     * 采购商账号错误
     */
    ILLEGAL_AGENT(2, "采购商账号错误"),
    /**
     * 参数错误
     */
    ILLEGAL_ARGUMENT(2, "参数错误"),
    /**
     * 接口已关闭
     */
    EXTERFACE_IS_CLOSED(2, "接口已关闭"),
    /**
     * 订单已取消
     */
    ORDER_IS_CLOSE(2, "订单已取消"),
    // /**
    // * 交易金额与订单的不一致
    // */
    // ACCOUNT_BALANCE_NOT_MATCH(2, "交易金额与订单的不一致"),
    /**
     * 账户余额不足
     */
    TRADE_TOTALFEE_NOT_ENOUGH(2, "账户余额不足"),
    /**
     * 系统错误
     */
    SYSTEM_ERROR(2, "系统错误");

    private final Integer code;
    private final String message;

    UheApiStatus(Integer c, String m) {
        code = c;
        message = m;
    }

    public Integer value() {
        return code;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public static UheApiStatus fromCode(Integer v) {
        for (UheApiStatus c : UheApiStatus.values()) {
            if (c.code.equals(v)) {
                return c;
            }
        }
        return null;
    }

}
