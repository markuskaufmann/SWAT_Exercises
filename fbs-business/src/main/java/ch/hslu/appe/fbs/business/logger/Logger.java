package ch.hslu.appe.fbs.business.logger;

import org.apache.logging.log4j.LogManager;

public final class Logger {

    private static final org.apache.logging.log4j.Logger LOG = LogManager.getLogger(Logger.class);

    private Logger() {
    }

    public static void logInfo(final Class<?> sourceClass, final String authenticatedUser, final String messageToLog) {
        LOG.info(constructLogString(sourceClass, authenticatedUser, messageToLog));
    }

    public static void logError(final Class<?> sourceClass, final String authenticatedUser, final String messageToLog) {
        LOG.error(constructLogString(sourceClass, authenticatedUser, messageToLog));
    }

    private static String constructLogString(final Class<?> sourceClass, final String authenticatedUser, final String messageToLog) {
        return sourceClass.getName() + " (" + authenticatedUser + "): " + messageToLog;
    }
}
