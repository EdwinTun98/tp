package seedu.duke;

import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.io.IOException;

/**
 * Serves as a utility class for logging messages to a file.
 * Configures logging to write to "mt.log" with INFO level by default.
 */
public class MTLogger {
    private static final String LOG_FILE_NAME = "mt.log";

    private final Logger logger;

    /**
     * Creates a logger instance for the specified class.
     * @param className The name of the class that will use this logger
     */
    public MTLogger(String className) {
        this.logger = Logger.getLogger(className);
        configureLogger();
    }

    /**
     * Configures the logger to write to a file with basic formatting.
     * Disables console output and sets log level to INFO.
     */
    private void configureLogger() {
        try {
            // This prevents log messages from being sent to the console
            logger.setUseParentHandlers(false);
            // Create a FileHandler to write logs to a file; true = append to existing file
            FileHandler fileHandler = new FileHandler(LOG_FILE_NAME, true);
            // Use simple text format for logs
            fileHandler.setFormatter(new SimpleFormatter());
            // Add the FileHandler to the logger
            logger.addHandler(fileHandler);

            // Set the logging level (e.g., INFO, FINE, WARNING)
            logger.setLevel(Level.INFO);
        } catch (IOException error) {
            logger.log(Level.SEVERE, "Error configuring logger: "
                    + error.getMessage(), error);
        }
    }

    /**
     * Logs an informational message.
     * @param message The message to log
     */
    public void logInfo(String message) {
        logger.info(message);
    }

    /**
     * Logs a warning message.
     * @param message The warning message to log
     */
    public void logWarning(String message) {
        logger.warning(message);
    }

    /**
     * Logs a severe error message with associated exception.
     * @param message The error message
     * @param error The associated Throwable/exception
     */
    public void logSevere(String message, Throwable error) {
        logger.log(Level.SEVERE, message, error);
    }
}
