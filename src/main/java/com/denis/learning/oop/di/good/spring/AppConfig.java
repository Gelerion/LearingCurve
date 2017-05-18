package com.denis.learning.oop.di.good.spring;

import com.denis.learning.oop.di.good.ReportBuilder;
import com.denis.learning.oop.di.good.ReportSender;
import com.denis.learning.oop.di.good.impl.ReportBuilderImpl;
import com.denis.learning.oop.di.good.impl.SmsReportSender;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public ReportBuilder reportBuilder() {
        return new ReportBuilderImpl();
    }

    @Bean
    public ReportSender reportSender() {
        return new SmsReportSender();
    }
}



