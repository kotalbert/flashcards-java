package flashcards;

/**
 * A class to log application events with output  to stdout and a file.
 */
public class Logger {
    private final StringBuilder log;

    public Logger() {
        log = new StringBuilder();
    }

    public void log(String message) {
        System.out.println(message);
        log.append(message).append(System.lineSeparator());
    }
    public void logToFile(String message) {
        log.append(message).append(System.lineSeparator());
    }

    public String getLog() {
        return log.toString();
    }
}
