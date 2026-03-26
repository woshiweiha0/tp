package seedu.duke.exceptions;

public class ResumakeException extends RuntimeException {
    public ResumakeException(String message) {
        super(message);
    }

    public ResumakeException(String message, Throwable cause) {
        super(message, cause);
    }
}
