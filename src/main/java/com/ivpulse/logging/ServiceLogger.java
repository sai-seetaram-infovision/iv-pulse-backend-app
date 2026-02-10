
package com.ivpulse.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServiceLogger {

    private final Logger logger;

    private ServiceLogger(Class<?> clazz) {
        this.logger = LoggerFactory.getLogger(clazz);
    }

    public static ServiceLogger get(Class<?> clazz) {
        return new ServiceLogger(clazz);
    }

    public void entering(String method) {
        logger.info("ENTERING {}", method);
    }

    public void exiting(String method) {
        logger.info("EXITING {}", method);
    }

    public void info(String msg, Object... args) {
        logger.info(msg, args);
    }

    public void error(String msg, Object... args) {
        logger.error(msg, args);
    }
}
