package com.uhetrip.api.service;

import java.lang.reflect.ParameterizedType;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.uhetrip.api.config.AppConfig;
import com.uhetrip.api.dto.common.ErrorInfo;
import com.uhetrip.api.dto.common.UheReq;
import com.uhetrip.api.dto.common.UheRsp;
import com.uhetrip.api.dto.enums.UheApiStatus;
import com.uhetrip.api.util.ApolloUtil;
import com.uhetrip.api.util.MD5Util;
import com.uhetrip.bean.auth.GetParentUserReq;
import com.uhetrip.bean.auth.GetParentUserResp;
import com.uhetrip.bean.enums.ApiStatus;
import com.uhetrip.bean.enums.UserType;
import com.uhetrip.bean.exception.BusinessException;
import com.uhetrip.bean.logging.LoggingReq;

public abstract class AbstractService<Q extends UheReq, R extends UheRsp> implements IService<Q, R> {

    @Resource
    protected IUheService uheService;
    @Resource
    protected AppConfig appConfig;
    private static final String CHARSET_UTF8 = "UTF-8";
    private static final String APOLLO_PREFIX_MD5_KEY = "md5.key.";

    private static final Logger logger = LoggerFactory.getLogger(AbstractService.class);
    private Class<R> clazzR;

    @SuppressWarnings("unchecked")
    public AbstractService() {
        ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
        clazzR = (Class<R>) type.getActualTypeArguments()[1];
    }

    @Override
    public R execute(Q req) throws Exception {
        R resp = null;
        try {
            resp = clazzR.newInstance();
        } catch (Exception e) {
            logger.error("Exception[newInstance]:", e);
        }

        // 0-null 1-Exception 2-BusinessExcepiton
        Integer throwExceptionLevel = 0;
        String level = "INFO";
        try {
            if (this.isLoggingAdd()) {
                if (logger.isDebugEnabled()) {
                    logger.debug("API-REQ-[{}]:{}", req.getAction(), JSON.toJSONString(req));
                }
            }
            if (StringUtils.isEmpty(req.getAction()) || !req.getAction().equals(this.getAction(req))) {
                resp.setErrorInfo(new ErrorInfo(UheApiStatus.ILLEGAL_ACTION));
                return resp;
            }
            if (StringUtils.isEmpty(req.getAgent())) {
                resp.setErrorInfo(new ErrorInfo(UheApiStatus.ILLEGAL_AGENT));
                return resp;
            }
            String md5Key = ApolloUtil.getApplicationValueByApolloKey(APOLLO_PREFIX_MD5_KEY + req.getAgent());
            if (StringUtils.isEmpty(md5Key)) {
                resp.setErrorInfo(new ErrorInfo(UheApiStatus.ILLEGAL_MD5_KEY));
                return resp;
            }
            if (appConfig.getSignVerifySwitch()) {
                if (StringUtils.isEmpty(req.getSign())) {
                    resp.setErrorInfo(new ErrorInfo(UheApiStatus.ILLEGAL_SIGN));
                    return resp;
                }
                if (!this.verifyReqSign(req, md5Key)) {
                    resp.setErrorInfo(new ErrorInfo(UheApiStatus.ILLEGAL_SIGN));
                    return resp;
                }
            }
            String paramCheck = this.paramCheck(req);
            if (StringUtils.isNotEmpty(paramCheck)) {
                resp.setErrorInfo(new ErrorInfo(UheApiStatus.ILLEGAL_ARGUMENT));
                return resp;
            }
            String agentId = getAgentId(req.getAgent());
            if (StringUtils.isEmpty(agentId)) {
                resp.setErrorInfo(new ErrorInfo(UheApiStatus.ILLEGAL_AGENT));
                return resp;
            }
            req.setAgentId(agentId);
            // 执行具体业务
            resp = process(req);
            resp.setSign(createRepSign(resp, md5Key));
        } catch (BusinessException e) {
            logger.error("BusinessException[{}]", req.getAction(), e);
            resp.setErrorInfo(new ErrorInfo(UheApiStatus.SYSTEM_ERROR));
            Integer code = e.getCode();
            if (code == null) {
                code = ApiStatus.FAILURE.value();
            }
            level = "ERROR";
            throwExceptionLevel = 2;
        } catch (Exception e) {
            logger.error("Exception[{}]", req.getAction(), e);
            resp.setErrorInfo(new ErrorInfo(UheApiStatus.SYSTEM_ERROR));
            level = "ERROR";
            throwExceptionLevel = 1;
        }
        if (this.isLoggingAdd()) {
            if (logger.isDebugEnabled()) {
                logger.debug("API-RESP-[{}]:{}", req.getAction(), JSON.toJSONString(resp));
            }
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("req", req);
            jsonObj.put("resp", resp);
            LoggingReq loggingReq = new LoggingReq(req.getAgentId(), req.getAgent(), "DistributionAPI", req.getAction(),
                level, JSON.toJSONString(jsonObj));
            uheService.loggingAdd(loggingReq);
        }
        if (throwExceptionLevel == 1) {
            throw new Exception(resp.getErrorInfo().getErrorMsg());
        } else if (throwExceptionLevel == 2) {
            throw new BusinessException(-1, resp.getErrorInfo().getErrorMsg());
        }
        return resp;
    }

    @Override
    public boolean isLoggingAdd() {
        return false;
    }

    public String getAction(UheReq req) {
        return req.getAction();
    }

    /**
     * 业务执行过程
     *
     * @param q
     * @return
     */
    public abstract R process(Q q) throws Exception;

    /**
     * 参数检验
     *
     * @param q
     * @return
     */
    public String paramCheck(Q q) {
        return null;
    }

    /**
     * 验证请求签名
     * 
     * @param q
     * @param md5Key
     * @return
     */
    private boolean verifyReqSign(Q q, String md5Key) {
        boolean verifyStatus = false;
        try {
            String sign = q.getSign();
            q.setSign(null);
            String createSign = MD5Util.md5(JSON.toJSONString(q) + md5Key, CHARSET_UTF8);
            if (sign.equals(createSign)) {
                verifyStatus = true;
            } else {
                logger.info("verifyReqSign. verifyStatus:{}, reqSign:{}, createSign:{}, md5Key:{}", verifyStatus, sign,
                    createSign, md5Key);
            }
        } catch (Exception e) {
            logger.error("Exception[verifyReqSign]:", e);
        }
        return verifyStatus;
    }

    /**
     * 生成响应签名
     * 
     * @param r
     * @param md5Key
     * @return
     */
    private String createRepSign(R r, String md5Key) {
        String createSign = null;
        try {
            createSign = MD5Util.md5(JSON.toJSONString(r) + md5Key, CHARSET_UTF8);
        } catch (Exception e) {
            logger.error("Exception[createRepSign]:", e);
        }
        return createSign;
    }

    /**
     * 获取供应商主账号
     * 
     * @param agent
     * @return
     */
    private String getAgentId(String agent) {
        String agentId = null;
        GetParentUserReq getParentUserReq = new GetParentUserReq();
        getParentUserReq.setUserType(UserType.SYSTEM.value());
        getParentUserReq.setUsername(UserType.SYSTEM.name());
        getParentUserReq.setUserId("1");
        getParentUserReq.setSearchUserType(UserType.BUYSER.value());
        getParentUserReq.setUsernameForQuery(agent);
        GetParentUserResp parentUserResp = uheService.getParentUser(getParentUserReq);
        if (parentUserResp.getStatus().equals(ApiStatus.SUCCESS.value())) {
            agentId = parentUserResp.getParent().getUserId();
        }
        return agentId;
    }

}
