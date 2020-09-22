package mate.academy.exceptions;

public class DataProcessingException extends RuntimeException {
    public DataProcessingException(String message, Throwable ex) {
        super(message, ex);
    }
}
