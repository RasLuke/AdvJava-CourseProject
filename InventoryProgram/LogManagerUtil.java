package InventoryProgram;

import java.io.IOException;
import java.util.logging.*;

public class LogManagerUtil {

    private static final Logger logger = Logger.getLogger("SmartStockLogger");

    static {
        setupLogger();
    }

    private static void setupLogger() {
        try {
            Logger rootLogger = Logger.getLogger("");
            Handler[] handlers = rootLogger.getHandlers();
            for (Handler h : handlers) {
                rootLogger.removeHandler(h);
            }

            // File handler (logs to file)
            Handler fileHandler = new FileHandler("smartstock.log", true);
            Formatter simpleFormatter = new SimpleFormatter();
            fileHandler.setFormatter(simpleFormatter);
            fileHandler.setLevel(Level.ALL);

            // Console handler (logs to console)
            ConsoleHandler consoleHandler = new ConsoleHandler();
            consoleHandler.setFormatter(simpleFormatter);
            consoleHandler.setLevel(Level.ALL);

            // Add handlers to logger
            logger.addHandler(fileHandler);
            logger.addHandler(consoleHandler);

            logger.setLevel(Level.ALL);
            logger.setUseParentHandlers(false); // Disable default console output

        } catch (IOException e) {
            System.err.println("Error setting up logger: " + e.getMessage());
        }
    }

    public static Logger getLogger() {
        return logger;
    }
}
