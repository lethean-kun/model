package com.uhetrip.api.config;

import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.scope.refresh.RefreshScope;
import org.springframework.stereotype.Component;

@Component
public class ApolloRefreshConfig {
    private static final Logger logger = LoggerFactory.getLogger(ApolloRefreshConfig.class);

    @Autowired
    private RefreshScope refreshScope;

    @Autowired
    private AppConfig appConfig;

    @ApolloConfigChangeListener({ "UHE.common", "application" })
    private void onChange(ConfigChangeEvent changeEvent) {
        logger.info("before refresh {}", appConfig.toString());
        refreshScope.refresh("appConfig");
        logger.info("after refresh {}", appConfig.toString());
    }
}