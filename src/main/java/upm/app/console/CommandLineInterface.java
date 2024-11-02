package upm.app.console;

import upm.app.data.modelos.TennisCourt;
import upm.app.data.modelos.User;
import upm.app.services.UserService;
import upm.app.services.CourtService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class CommandLineInterface {

    private static final String COMMAND_DELIMITER_PARAMETERS = "["+Delimiters.COMMAND.getValue()    +"\\r\\n]";

    private final UserService userService;
    private final CourtService courtService;
    private final View view;
    private User user;

    public CommandLineInterface(UserService userService, CourtService courtService, View view) {
        this.userService = userService;
        this.courtService=courtService;
        this.view = view;
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
        if (!Objects.isNull(this.user)) {
            this.view.showCommand(this.user.getName());
        } else {
            this.view.showCommand();
        }
        CommandNames command = CommandNames.fromValue(scanner.next());
        String[] params=this.getParamsIfNeededAssured(scanner, command);
        boolean exit = false;
        switch (command) {
            case LOGIN -> this.login(params);
            case LOGOUT -> this.logout();
            case CREATE_USER -> this.createUser(params);
            case DELETE_USER -> this.deleteByDni(params);
            case FIND_ALL_USER -> this.listAll();
            case CREATE_COURT -> this.createCourt(params);
            case DELETE_COURT -> this.deleteByName(params);
            case FIND_ALL_COURT -> this.listAllCourt();
            case HELP -> this.help();
            case EXIT -> exit = true;
            default -> throw new IllegalArgumentException("El comando " + command + " no existe");
        }
        return exit;
    }

    private String[] getParamsIfNeededAssured(Scanner scanner, CommandNames command) {
        if (command.getParams().length > 0) {
            String[] params = scanner.next().split(Delimiters.PARAM.getValue());
            if (command.getParams().length != params.length) {
                throw new IllegalArgumentException("Parámetros esperados: " + Arrays.toString(command.getParams()) +
                        ", encontrados " + Arrays.toString(params));
            }
            return params;
        }
        return new String[0];
    }

    private void login(String[] values){
        this.user=this.userService.login(values[0], values[1]);
    }

    private void logout(){
        this.user=null;
    }

    private void createUser(String[] values) {
        if (values.length != 4) {
            throw new IllegalArgumentException(CommandNames.CREATE_USER.getHelp());
        }
        User createdUser = this.userService.create(new User(values[0], LocalDate.parse(values[1]), values[2], values[3]));
        this.view.show(createdUser.toString());
    }

    private void deleteByDni(String[] values) {
        if (values.length != 1) {
            throw new IllegalArgumentException(CommandNames.DELETE_USER.getHelp());
        }
        this.userService.deleteByDni(values[0]);
        this.view.show("Usuario borrado");
    }

    private void listAll() {
        List<User> list = this.userService.listAll();
        this.view.show(list.toString());
    }

    private void createCourt(String[] values){
        if (values.length!=3){
            throw new IllegalArgumentException(CommandNames.CREATE_COURT.getHelp());
        }
        TennisCourt createdCourt = this.courtService.create(new TennisCourt(values[0], values[1], values[2]));
        this.view.show(createdCourt.toString());
    }

    private void deleteByName(String[] values){
        if (values.length!=1){
            throw new IllegalArgumentException(CommandNames.DELETE_COURT.getHelp());
        }
        this.courtService.deleteByName(values[0]);
        this.view.show("Pista borrada");
    }

    private void listAllCourt(){
        List<TennisCourt> list=this.courtService.listAll();
        this.view.show(list.toString());
    }

    private void help() {
        for (CommandNames command : CommandNames.values()) {
            this.view.show(command.getHelp());
        }
    }


}
