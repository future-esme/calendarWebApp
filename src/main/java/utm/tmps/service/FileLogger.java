package utm.tmps.service;

import static utm.tmps.utils.DateUtil.formatter;

import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;

public class FileLogger implements Logger {

    @Override
    public void debug(String message) {
        try (FileWriter writer = new FileWriter("log.txt", true)) {
            var entireMessage = getMessage(message, "DEBUG");
            writer.write(entireMessage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void error(String message) {
        try (FileWriter writer = new FileWriter("log.txt", true)) {
            var entireMessage = getMessage(message, "INFO");
            writer.write(entireMessage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void info(String message) {
        try (FileWriter writer = new FileWriter("log.txt", true)) {
            var entireMessage = getMessage(message, "ERROR");
            writer.write(entireMessage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getMessage(String message, String level) {
        return new StringBuilder(formatter.format(Instant.now()))
            .append("   ")
            .append(level)
            .append("   :")
            .append(message)
            .append("\n")
            .toString();
    }
}
