package com.denis.learning.oop.di.bad;

import java.util.List;

public class Reporter {

    public void sendReports() {
        ReportBuilder reportBuilder = new ReportBuilder();
        List<Report> reports = reportBuilder.createReports();

        if(reports.size() == 0) {
            throw new NoReportsException();
        }

        EmailReportSender reportSender = new EmailReportSender();
        for (Report report : reports) {
            reportSender.send(report);
        }
    }
}




