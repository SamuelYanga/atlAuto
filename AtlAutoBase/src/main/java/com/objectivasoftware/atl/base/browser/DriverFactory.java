package com.objectivasoftware.atl.base.browser;

import org.apache.log4j.Logger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DriverFactory {
    private static Logger LOGGER = Logger.getLogger(DriverFactory.class);

    private static List<DriverThread> browserThreadPool = Collections.synchronizedList(new ArrayList<DriverThread>());
    private static ThreadLocal<DriverThread> browserThread;

    static {
        DriverFactory.instantiateDriverObject();

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                try {
                    DriverFactory.closeBrowserObjects();
                } catch (Exception e) {
                    LOGGER.error("Destroy browsers Error!");
                }
            }
        });
    }

    public static void instantiateDriverObject() {
        browserThread = new ThreadLocal<DriverThread>() {
            @Override
            protected DriverThread initialValue() {
                DriverThread webDriverThread = new DriverThread();
                browserThreadPool.add(webDriverThread);
                return webDriverThread;
            }
        };
    }

    public static MyDriver getBrowser() {
        return browserThread.get().getDriver();
    }

    public static void clearCookies() {
        getBrowser().manage().deleteAllCookies();
    }
    
    public static void closeBrowserObjects() {
        for (DriverThread browserThread : browserThreadPool){
            browserThread.quitBrowser();
        }
    }
}
