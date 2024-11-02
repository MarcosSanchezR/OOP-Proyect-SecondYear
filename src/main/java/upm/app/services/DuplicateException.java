package upm.app.services;

public class DuplicateException extends RuntimeException{
    private static final String DESCRIPTION = "Duplicate exception: ";

    public DuplicateException(String detail){
        super(DESCRIPTION + detail);
    }
}
