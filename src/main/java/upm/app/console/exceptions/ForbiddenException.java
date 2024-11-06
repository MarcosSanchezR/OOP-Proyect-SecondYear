package upm.app.console.exceptions;

public class ForbiddenException extends RuntimeException {
    private static final String DESCRIPTION = "Prohibido. No tiene permisos suficientes";

    public ForbiddenException(String detail) {
        super(DESCRIPTION + ". " + detail);
    }

}
