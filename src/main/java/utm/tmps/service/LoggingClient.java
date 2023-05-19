package utm.tmps.service;

public class LoggingClient {

    private Logger logger;

    public void setMyLogger(Logger myLogger) {
        this.logger = myLogger;
    }

    public void debug(String message) {
        if (logger != null) {
            logger.debug(message);
        } else {
            System.out.println("Logger not set.");
        }
    }

    public void info(String message) {
        if (logger != null) {
            logger.info(message);
        } else {
            System.out.println("Logger not set.");
        }
    }

    public void error(String message) {
        if (logger != null) {
            logger.error(message);
        } else {
            System.out.println("Logger not set.");
        }
    }
}
