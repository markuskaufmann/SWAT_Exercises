package ch.hslu.edu.enapp.webshop.exception;

public class CheckoutFailedException extends Exception {

    public CheckoutFailedException() {
        super();
    }

    public CheckoutFailedException(String message) {
        super(message);
    }

    public CheckoutFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
