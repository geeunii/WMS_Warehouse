package util.input;

public class InputException extends RuntimeException {
    public InputException(String message, Throwable cause) { super(message, cause); }
    public InputException(String message) { super(message); }
}
