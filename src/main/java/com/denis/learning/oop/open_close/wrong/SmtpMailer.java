package com.denis.learning.oop.open_close.wrong;

import static java.lang.String.format;

public class SmtpMailer {
    private DatabaseLogger logger;

    public SmtpMailer() {
        this.logger = new DatabaseLogger();
    }

    public void sendMessage(String message) {
        logger.log(format("Sent '{%s}'", message));
    }
}
