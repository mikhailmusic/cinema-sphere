package rut.miit.cinema.exception;

public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }

    public ValidationException() {
        super("Validation failed. Please check the data provided.");
    }
}