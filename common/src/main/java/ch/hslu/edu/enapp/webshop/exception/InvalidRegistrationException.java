package ch.hslu.edu.enapp.webshop.exception;

public class InvalidRegistrationException extends Exception {

    public InvalidRegistrationException() {
        super();
    }

    public InvalidRegistrationException(String message) {
        super(message);
    }

    public InvalidRegistrationException(String message, Throwable cause) {
        super(message, cause);
    }
}
