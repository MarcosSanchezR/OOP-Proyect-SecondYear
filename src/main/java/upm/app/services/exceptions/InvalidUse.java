package upm.app.services.exceptions;

public class InvalidUse extends RuntimeException{
    private static final String DESCRIPTION = "Invalid use: ";

    public InvalidUse(String detail) {
        super(DESCRIPTION + ". " + detail);
    }
}
