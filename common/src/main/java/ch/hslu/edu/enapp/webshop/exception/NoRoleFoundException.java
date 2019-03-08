package ch.hslu.edu.enapp.webshop.exception;

public class NoRoleFoundException extends Exception {

    public NoRoleFoundException() {
        super();
    }

    public NoRoleFoundException(String message) {
        super(message);
    }

    public NoRoleFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
