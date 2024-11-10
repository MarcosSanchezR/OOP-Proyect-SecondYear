package upm.app.console;

import upm.app.console.exceptions.BadRequestException;
import upm.app.data.modelos.Match;
import upm.app.data.modelos.Rol;
import upm.app.data.modelos.TennisCourt;
import upm.app.data.modelos.User;
import upm.app.services.CourtService;
import upm.app.services.MatchService;
import upm.app.services.UserService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class CommandLineInterface {

    private static final String COMMAND_DELIMITER_PARAMETERS = "[" + Delimiters.COMMAND.getValue() + "\\r\\n]";

    private final UserService userService;
    private final CourtService courtService;
    private final MatchService matchService;
    private final View view;
    private User user;

    public CommandLineInterface(UserService userService, CourtService courtService, MatchService matchService, View view) {
        this.userService = userService;
        this.courtService = courtService;
        this.matchService = matchService;
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
        this.view.showCommand(userName());
        CommandNames command = CommandNames.fromValue(scanner.next(), this.userRol());
        String[] params = this.getParamsIfNeededAssured(scanner, command);
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
            case CREATE_MATCH -> this.createMatch(params);
            case ESTABLISH_WINNER -> this.establishWinner(params);
            case FIND_ALL_MATCH -> this.listAllMatch();
            case HELP -> this.help();
            case EXIT -> exit = true;
            default -> throw new UnsupportedOperationException("El comando " + command + " no existe");
        }
        return exit;
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

    private String[] getParamsIfNeededAssured(Scanner scanner, CommandNames command) {
        if (command.getParams().length > 0) {
            String[] params = scanner.next().split(Delimiters.PARAM.getValue());
            if (command.getParams().length != params.length) {
                throw new BadRequestException("Parámetros esperados: " + Arrays.toString(command.getParams()) +
                        ", encontrados " + Arrays.toString(params));
            }
            return params;
        }
        return new String[0];
    }

    private void login(String[] values) {
        this.user = this.userService.login(values[0], values[1]);
    }

    private void logout() {
        this.user = null;
    }

    private void createUser(String[] values) {
        User createdUser = this.userService.create(new User(values[0], LocalDate.parse(values[1]), values[2], values[3]));
        this.view.show(createdUser.toString());
    }

    private void deleteByDni(String[] values) {
        this.userService.deleteByDni(values[0]);
        this.view.show("Usuario borrado");
    }

    private void listAll() {
        List<User> list = this.userService.listAll();
        this.view.show(list.toString());
    }

    private void createCourt(String[] values) {
        TennisCourt createdCourt = this.courtService.create(new TennisCourt(values[0], values[1], values[2]));
        this.view.show(createdCourt.toString());
    }

    private void deleteByName(String[] values) {
        this.courtService.deleteByName(values[0]);
        this.view.show("Pista borrada");
    }

    private void listAllCourt() {
        List<TennisCourt> list = this.courtService.listAll();
        this.view.show(list.toString());
    }

    private void createMatch(String[] value) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH-mm-ss");
        Match createdMatch = this.matchService.create(LocalDateTime.parse(value[0], formatter), value[1], value[2], value[3]);
        this.view.show(createdMatch.toString());
    }

    private void establishWinner(String[] values) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH-mm-ss");
        this.matchService.establishWinner(LocalDateTime.parse(values[0], formatter), values[1], values[2]);
        this.view.show("Ganador establecido");
    }

    private void listAllMatch() {
        List<Match> list = this.matchService.listAll();
        this.view.show(list.toString());
    }

    private void help() {
        for (CommandNames command : CommandNames.values()) {
            if (!command.getHelp(userRol()).isEmpty()) {
                this.view.show(command.getHelp(userRol()));
            }
        }
    }


}
