package utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggerUtil {

    private static Logger logger = LogManager.getLogger(LoggerUtil.class);

    public static void info(String msg) {
        logger.info(msg);
    }

    public static void error(String msg) {
        logger.error(msg);
    }
}