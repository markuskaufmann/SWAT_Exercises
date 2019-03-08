package ch.hslu.edu.enapp.webshop.exception;

public class NoCustomerLoggedInException extends Exception {

    public NoCustomerLoggedInException() {
        super();
    }

    public NoCustomerLoggedInException(String message) {
        super(message);
    }

    public NoCustomerLoggedInException(String message, Throwable cause) {
        super(message, cause);
    }
}
