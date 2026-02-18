package ui;

/**
 * Parses user input into commands and arguments.
 */
public class Parser {
    /**
     * Returns the command word (first word) from the user input.
     */
    public static String getCommand(String input) {
        String[] parts = input.trim().split(" ", 2);
        return parts[0];
    }

    /**
     * Returns the arguments (everything after the command word) from the user input.
     */
    public static String getArguments(String input) {
        String[] parts = input.trim().split(" ", 2);
        return parts.length > 1 ? parts[1] : "";
    }
}
