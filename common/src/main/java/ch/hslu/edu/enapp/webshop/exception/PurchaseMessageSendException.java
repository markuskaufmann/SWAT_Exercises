package ch.hslu.edu.enapp.webshop.exception;

public class PurchaseMessageSendException extends Exception {

    public PurchaseMessageSendException() {
        super();
    }

    public PurchaseMessageSendException(String message) {
        super(message);
    }

    public PurchaseMessageSendException(String message, Throwable cause) {
        super(message, cause);
    }
}
