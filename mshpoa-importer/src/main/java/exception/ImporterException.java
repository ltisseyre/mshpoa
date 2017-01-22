package exception;

/**
 * Created by ltisseyre on 22/01/17.
 */
public class ImporterException extends Exception {

    public ImporterException(String message) {
        super(message);
    }

    public ImporterException(String message, Throwable cause) {
        super(message, cause);
    }

    public ImporterException(Throwable cause) {
        super(cause);
    }
}
