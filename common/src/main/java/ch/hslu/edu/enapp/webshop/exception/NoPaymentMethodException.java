package ch.hslu.edu.enapp.webshop.exception;

public class NoPaymentMethodException extends Exception {

    public NoPaymentMethodException() {
        super();
    }

    public NoPaymentMethodException(String message) {
        super(message);
    }

    public NoPaymentMethodException(String message, Throwable cause) {
        super(message, cause);
    }
}
