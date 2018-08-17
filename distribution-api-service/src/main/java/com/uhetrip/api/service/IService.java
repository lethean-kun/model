package com.uhetrip.api.service;

import com.uhetrip.api.dto.common.UheReq;
import com.uhetrip.api.dto.common.UheRsp;

public interface IService<Q extends UheReq, R extends UheRsp> {

    /**
     * 业务执行入口
     * 
     * @param q
     * @return
     */
    R execute(Q q) throws Exception;

    /**
     * 添加日志
     * 
     * @return
     */
    boolean isLoggingAdd();
}
