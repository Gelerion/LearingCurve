package com.denis.learning.oop.di.good.spring;

import com.denis.learning.oop.di.bad.NoReportsException;
import com.denis.learning.oop.di.bad.Report;
import com.denis.learning.oop.di.good.ReportBuilder;
import com.denis.learning.oop.di.good.ReportSender;
import com.denis.learning.oop.di.good.Reporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReporterSpring implements Reporter {

    @Autowired
    private ReportBuilder reportBuilder;
    @Autowired
    private ReportSender reportSender;


    @Override
    public void sendReports() {
        List<Report> reports = reportBuilder.createReports();

        if(reports.size() == 0) {
            throw new NoReportsException();
        }

        reports.forEach(reportSender::send);
    }
}

