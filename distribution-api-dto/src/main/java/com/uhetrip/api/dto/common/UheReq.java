package com.uhetrip.api.dto.common;

/**
 * 优合采购商API请求公共信息
 * 
 * @author lihaipeng
 * @Date 2018年7月12日 上午10:34:40
 */
public class UheReq {
    private String action;
    // 采购商ID
    private String agentId;
    // 采购商账号
    private String agent;
    private String sign;
    private Long timestamp;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

}
