package ch.hslu.edu.enapp.webshop.exception;

public class NoCustomerFoundException extends Exception {

    public NoCustomerFoundException() {
        super();
    }

    public NoCustomerFoundException(String message) {
        super(message);
    }

    public NoCustomerFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
