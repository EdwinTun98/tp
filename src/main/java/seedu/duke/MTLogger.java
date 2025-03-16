package seedu.duke;

import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.io.IOException;

public class MTLogger {
    private final Logger logger;

    public MTLogger(String className) {
        this.logger = Logger.getLogger(className);
        configureLogger();
    }

    private void configureLogger() {
        try {
            // Create a FileHandler to write logs to a file; true = append to existing file
            FileHandler fileHandler = new FileHandler("mt.log",
                    true);
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

    public void logInfo(String message) {
        logger.info(message);
    }

    public void logWarning(String message) {
        logger.warning(message);
    }

    public void logSevere(String message, Throwable error) {
        logger.log(Level.SEVERE, message, error);
    }
}
