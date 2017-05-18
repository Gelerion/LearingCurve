package com.denis.learning.oop.open_close.right;

public interface Logger {
    void log(String logText);

    class FileLogger implements Logger {
        @Override
        public void log(String logText) {
            //log to file
        }
    }

    class DatabaseLogger implements Logger {
        @Override
        public void log(String logText) {
            //save to database
        }
    }
}




