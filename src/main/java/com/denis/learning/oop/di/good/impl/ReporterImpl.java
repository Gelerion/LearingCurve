package com.denis.learning.oop.di.good.impl;

import com.denis.learning.oop.di.bad.NoReportsException;
import com.denis.learning.oop.di.bad.Report;
import com.denis.learning.oop.di.good.ReportBuilder;
import com.denis.learning.oop.di.good.ReportSender;
import com.denis.learning.oop.di.good.Reporter;

import java.util.List;

public class ReporterImpl implements Reporter {
    private ReportBuilder reportBuilder;
    private ReportSender reportSender;

    public ReporterImpl(ReportBuilder reportBuilder,
                        ReportSender reportSender) {
        this.reportBuilder = reportBuilder;
        this.reportSender = reportSender;
    }

    @Override
    public void sendReports() {
        List<Report> reports = reportBuilder.createReports();

        if(reports.size() == 0) {
            throw new NoReportsException();
        }

        reports.forEach(reportSender::send);
    }
}




