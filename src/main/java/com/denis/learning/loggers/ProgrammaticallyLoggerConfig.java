package com.denis.learning.loggers;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.invoke.MethodHandles;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.ConsoleAppender;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.ConfigurationFactory;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.config.xml.XmlConfigurationFactory;
import org.apache.logging.log4j.core.layout.PatternLayout;

/**
 * Property ConfigurationFactory will look for log4j2-test.properties in the classpath.
 * YAML ConfigurationFactory will look for log4j2-test.yaml or log4j2-test.yml in the classpath.
 * JSON ConfigurationFactory will look for log4j2-test.jsn or log4j2-test.json in the classpath.
 * XML ConfigurationFactory will look for log4j2-test.xml in the classpath.
 * Property ConfigurationFactory will look for log4j2.properties on the classpath
 * YAML ConfigurationFactory will look for log4j2.yml or log4j2.yaml in the classpath.
 * JSON ConfigurationFactory will look for log4j2.jsn or log4j2.json in the classpath.
 * XML ConfigurationFactory will look for log4j2.xml in the classpath.
 * If no configuration file was provided, the DefaultConfiguration takes place and that would lead
 */
public class ProgrammaticallyLoggerConfig {
    public static void main(String[] args) throws IOException {
        // Get instance of configuration factory; your options are default
        // ConfigurationFactory, XMLConfigurationFactory, YamlConfigurationFactory & JsonConfigurationFactory
        ConfigurationFactory factory = XmlConfigurationFactory.getInstance();

        // Locate the source of this configuration, this located file is dummy file contains just an empty configuration Tag
        ConfigurationSource configurationSource = new ConfigurationSource(new FileInputStream(
                "/home/denis-shuvalov/Development/Learning Projects/RpcInvoke/src/main/java/com/denis/learning/loggers/customLoggerConf.xml"));

        Configuration configuration = factory.getConfiguration(LoggerContext.getContext(), configurationSource);
        ConsoleAppender appender = ConsoleAppender.createDefaultAppenderForLayout(PatternLayout.createDefaultLayout());
        configuration.addAppender(appender);

        LoggerConfig loggerConfig = new LoggerConfig("com", Level.FATAL,false);
        loggerConfig.addAppender(appender, null, null);
        configuration.addLogger("com", loggerConfig);

        LoggerContext context = new LoggerContext(MethodHandles.lookup().lookupClass().getSimpleName());
        context.start(configuration);

        // Get a reference for logger
        Logger logger = context.getLogger("com");

        // LogEvent of DEBUG message
        logger.log(Level.FATAL, "Logger Name :: "+logger.getName()+" :: Passed Message ::");

        // LogEvent of Error message for Logger configured as FATAL
        logger.log(Level.ERROR, "Logger Name :: "+logger.getName()+" :: Not Passed Message ::");

        // LogEvent of ERROR message that would be handled by Root
        logger.getParent().log(Level.ERROR, "Root Logger :: Passed Message As Root Is Configured For ERROR Level messages");
    }
}
