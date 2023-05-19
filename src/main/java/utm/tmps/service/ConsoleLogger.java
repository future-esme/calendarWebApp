package utm.tmps.service;

import static utm.tmps.utils.DateUtil.formatter;

import java.time.Instant;

public class ConsoleLogger implements Logger {

    @Override
    public void debug(String message) {
        System.out.println(getMessage(message, "DEBUG"));
    }

    @Override
    public void info(String message) {
        System.out.println(getMessage(message, "INFO"));
    }

    @Override
    public void error(String message) {
        System.out.println(getMessage(message, "ERROR"));
    }

    private String getMessage(String message, String level) {
        return new StringBuilder(formatter.format(Instant.now())).append("   ").append(level).append("   :").append(message).toString();
    }
}
