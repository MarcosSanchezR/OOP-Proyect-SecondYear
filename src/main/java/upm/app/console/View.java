package upm.app.console;

public class View {
    public static final String RESET = "\u001B[0m";
    public static final String BLACK = "\u001B[30m";
    public static final String CYAN = "\u001B[36m";
    public static final String BACKGROUND_WHITE = "\u001B[47m";
    public static final String BACKGROUND_RED = "\u001B[41m";
    public static final String COPY_RIGHT = "©";

    public void show(String message) {
        System.out.println(View.CYAN + "   - " + message + View.RESET);
    }

    public void showBold(String message) {
        System.out.println(View.BACKGROUND_WHITE + View.BLACK + "  " + message + "  " + View.RESET);
    }

    public void showError(String message) {
        System.out.println(BACKGROUND_RED + upm.app.console.View.BLACK + "  " + message + "  " + upm.app.console.View.RESET);
    }


}