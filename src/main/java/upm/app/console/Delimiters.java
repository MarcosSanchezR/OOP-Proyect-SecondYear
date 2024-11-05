package upm.app.console;

public enum Delimiters {
    COMMAND(":"), PARAM(",");

    private final String value;

    Delimiters(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
