package upm.app.console;

public class Help {
    private static final String HELP_CREATE_U= ":<nombre>,<aaaa-mm-dd>,<dni>";
    private static final String HELP_DELETE_BY_DNI= ":<dni>";

    public String createUser(){
        return HELP_CREATE_U;
    }
    public String deleteByDni() {
        return HELP_DELETE_BY_DNI;
    }
}
