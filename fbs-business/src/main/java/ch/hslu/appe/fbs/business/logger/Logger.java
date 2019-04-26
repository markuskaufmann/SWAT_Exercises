package ch.hslu.appe.fbs.business.logger;

import org.apache.logging.log4j.LogManager;

public final class Logger {

    private static final org.apache.logging.log4j.Logger LOG = LogManager.getLogger(Logger.class);

    private Logger() {
    }

    public static void logInfo(final Class<?> sourceClass, final String authenticatedUser, final String messageToLog) {
        final String message = constructLogMessage(sourceClass, authenticatedUser, messageToLog);
        LOG.info(message);
    }

    public static void logError(final Class<?> sourceClass, final String authenticatedUser, final String messageToLog) {
        final String message = constructLogMessage(sourceClass, authenticatedUser, messageToLog);
        LOG.error(message);
    }

    private static String constructLogMessage(final Class<?> sourceClass, final String authenticatedUser, final String messageToLog) {
        return sourceClass.getName() + " (" + authenticatedUser + "): " + messageToLog;
    }
}
