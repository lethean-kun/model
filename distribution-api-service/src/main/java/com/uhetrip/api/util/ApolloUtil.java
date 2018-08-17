package com.uhetrip.api.util;

import com.ctrip.framework.apollo.ConfigService;
import com.uhetrip.util.constant.Constant;

/**
 * Apollo工具包
 */
public class ApolloUtil {

    /**
     * 获取Apollo配置
     * 
     * @param apolloKey
     * @return
     */
    public static String getApplicationValueByApolloKey(String apolloKey) {
        return ConfigService.getConfig("application").getProperty(apolloKey, Constant.BLANK);
    }

    /**
     * 获取Apollo配置
     * 
     * @param apploKey
     * @return
     */
    public static String getCommonValueByApolloKey(String apploKey) {
        return ConfigService.getConfig("UHE.common").getProperty(apploKey, Constant.BLANK);
    }
}