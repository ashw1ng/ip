package exceptions;

/**
 * Custom exception class for the UrMum chatbot application.
 * Used to indicate application-specific errors.
 */
public class UrmumException extends Exception {
    public UrmumException(String message) {
        super(message);
    }
}
