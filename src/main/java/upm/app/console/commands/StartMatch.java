package upm.app.console.commands;

import upm.app.console.Command;
import upm.app.console.View;
import upm.app.data.modelos.Rol;
import upm.app.services.MatchService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class StartMatch implements Command {
    private final View view;
    private final MatchService matchService;

    public StartMatch(View view, MatchService matchService) {
        this.view = view;
        this.matchService = matchService;
    }

    @Override
    public String name() {
        return "start-match";
    }

    @Override
    public List<String> params() {
        return List.of("<dd-MM-yyyy HH-mm-ss>", "<nombre-pista>");
    }

    @Override
    public List<Rol> allowedRoles() {
        return List.of(Rol.ADMIN, Rol.REFEREE);
    }

    @Override
    public String helpMessage() {
        return "Empieza el partido";
    }

    @Override
    public void execute(String[] params) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH-mm-ss");
        this.matchService.startMatch(LocalDateTime.parse(params[0], formatter), params[1]);
        this.view.show("El partido ha empezado");
    }
}
