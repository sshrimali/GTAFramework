package utils.framework;

import org.apache.log4j.*;

import java.io.FileInputStream;
import java.nio.file.Paths;
import java.util.Properties;

import static utils.framework.BaseDriver.basePath;
import static utils.framework.BaseDriver.browserAgent;

/**
 * Created by sshrimali on 12/19/16.
 */
public class Logger  {
    public final static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(Logger.class);
    private String logLevel;

    public  void setGlobalProperties() throws Exception {
        // read properties from config file that are applibale to framework
        Properties globalProperties = new Properties();
        FileInputStream config = new FileInputStream("config/config.cfg");
        globalProperties.load(config);
        config.close();
        logLevel=globalProperties.getProperty("LogLevel","INFO");
    }
    public void initialize(String suiteName)
    {
        logger.removeAllAppenders();
        //create appender
        ConsoleAppender console = new ConsoleAppender();
        //configure the appender
        String PATTERN = "%d [%p|%c|%C{1}] %m%n";
        console.setLayout(new PatternLayout(PATTERN));
        console.setThreshold(Priority.toPriority(logLevel));
        console.activateOptions();

        FileAppender fa = new FileAppender();
        fa.setName("FileLogger");
        fa.setFile("logs/framework.log");
        fa.setLayout(new PatternLayout("%d %-5p [%c{1}] %m%n"));
        fa.setThreshold(Priority.toPriority(logLevel));
        fa.setAppend(true);
        fa.activateOptions();
        logger.addAppender(fa);
        logger.addAppender(console);

    }
}
