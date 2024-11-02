package upm.app.services;

public class UnauthorizedException extends RuntimeException{
    private static final String DESCRIPTION = "Unauthorized exception: ";

    public UnauthorizedException(String detail) {
        super(DESCRIPTION + detail);
    }
}
