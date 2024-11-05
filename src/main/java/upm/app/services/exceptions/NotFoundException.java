package upm.app.services.exceptions;

public class NotFoundException extends RuntimeException {
    private static final String DESCRIPTION = "Not found exception: ";

    public NotFoundException(String detail) {
        super(DESCRIPTION + ". " + detail);
    }
}
