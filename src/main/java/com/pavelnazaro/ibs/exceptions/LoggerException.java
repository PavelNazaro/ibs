package com.pavelnazaro.ibs.exceptions;

import com.pavelnazaro.ibs.controllers.DocumentController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggerException extends RuntimeException {
    private static final Logger logger = LogManager.getLogger(DocumentController.class);
    public LoggerException(String message) {
        super(message);
        logger.error(message);
    }
}
