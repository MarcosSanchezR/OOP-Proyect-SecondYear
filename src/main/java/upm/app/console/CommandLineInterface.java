package upm.app.console;

import upm.app.console.exceptions.BadRequestException;
import upm.app.console.exceptions.ForbiddenException;
import upm.app.data.modelos.Match;
import upm.app.data.modelos.Rol;
import upm.app.data.modelos.TennisCourt;
import upm.app.data.modelos.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class CommandLineInterface {

    private static final String EXIT="exit";
    private static final String COMMAND_DELIMITER_PARAMETERS = "[" + Delimiters.COMMAND.getValue() + "\\r\\n]";

    private final Map<String, Command> commands;
    private final View view;
    private User user;

    public CommandLineInterface(View view) {
        this.view = view;
        this.commands=new HashMap<>();
    }

    public void add(Command command){
        this.commands.put(command.name(), command);
    }

    public boolean runCommands() {
        Scanner scanner = new Scanner(System.in).useDelimiter(COMMAND_DELIMITER_PARAMETERS);
        boolean exit;
        do {
            exit = runCommands(scanner);
        } while (!exit);
        return true;
    }

    public boolean runCommands(Scanner scanner) {
        this.view.showCommand(this.userName());
        String command=scanner.next();
        if (this.commands.containsKey(command)){
            throw new BadRequestException("El comando ("+command+") no existe");
        }
        if (!this.commands.get(command).allowedRoles().contains(this.userRol())) {
            throw new ForbiddenException("Rol actual: " + this.userRol() + ", roles permitidos: " + this.commands.get(command).allowedRoles());
        }
        String[] params = this.scanParamsIfNeededAssured(scanner, command);
        if (EXIT.equals(command)) {
            return true;
        } else {
            this.commands.get(command).execute(params);
        }
        return false;


    }

    private String userName() {
        if (Objects.isNull(this.user)) {
            return "";
        } else {
            return this.user.getName();
        }
    }

    private Rol userRol() {
        if (Objects.isNull(this.user)) {
            return Rol.NONE;
        } else {
            return this.user.getRol();
        }
    }

    private String[] scanParamsIfNeededAssured(Scanner scanner, String command) {
        List<String> expected = commands.get(command).params();
        if (expected.isEmpty()) {
            return new String[0];
        }
        String[] foundParams = scanner.next().split(Delimiters.PARAM.getValue());
        if (expected.size() != foundParams.length) {
            throw new BadRequestException("Parámetros esperados: " + expected + ", encontrados " + Arrays.toString(foundParams));
        }
        return foundParams;
    }

    public void help() {
        for (Command command : this.commands.values()) {
            if (command.allowedRoles().contains(this.userRol())) {
                this.view.showBold(command.help());
            }
        }
    }

    public void setUser(User user){
        this.user=user;
    }


}
