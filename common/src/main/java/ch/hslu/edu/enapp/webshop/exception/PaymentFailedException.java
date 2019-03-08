package ch.hslu.edu.enapp.webshop.exception;

public class PaymentFailedException extends Exception {

    public PaymentFailedException() {
        super();
    }

    public PaymentFailedException(String message) {
        super(message);
    }

    public PaymentFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
