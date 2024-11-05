package upm.app.data.modelos;

public class InvalidAttributeException extends RuntimeException {
    private static final String DESCRIPTION = "Invalid attribute exception: ";

    public InvalidAttributeException(String detail) {
        super(DESCRIPTION + ". " + detail);
    }
}
