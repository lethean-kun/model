package com.uhetrip.api.boot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @author yuzhigang on 3/6/2018 10:15 PM.
 * @version 1.0 Description:
 */
@Component
public class Bootstrap implements ApplicationRunner {
    private static final Logger logger = LoggerFactory.getLogger(Bootstrap.class);

    /**
     * Do initialization task here. The method will be invoked in the main thread just after
     * applicationcontext is created and before springboot application startup
     *
     * @param args
     * @throws Exception
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("Your application started with option names : {}", args.getOptionNames());
    }
}
