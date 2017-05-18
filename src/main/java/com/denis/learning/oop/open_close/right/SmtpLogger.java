package com.denis.learning.oop.open_close.right;

import static java.lang.String.format;

public class SmtpLogger {
    private Logger logger;

    public SmtpLogger(Logger logger) {this.logger = logger;}

    public void sendMessage(String message) {
        logger.log(format("Sent '{%s}'", message));
    }
}
