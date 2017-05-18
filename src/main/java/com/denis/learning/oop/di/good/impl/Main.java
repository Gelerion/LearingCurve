package com.denis.learning.oop.di.good.impl;

import com.denis.learning.oop.di.good.ReportBuilder;
import com.denis.learning.oop.di.good.ReportSender;
import com.denis.learning.oop.di.good.Reporter;

public class Main {
    public static void main(String[] args) {

        ReportBuilder reportBuilder = new ReportBuilderImpl();
        ReportSender reportSender = new SmsReportSender();
        Reporter reporter = new ReporterImpl(reportBuilder, reportSender);
    }
}
