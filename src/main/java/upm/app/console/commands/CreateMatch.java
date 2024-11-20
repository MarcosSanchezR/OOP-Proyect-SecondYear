package upm.app.console.commands;

import upm.app.console.Command;
import upm.app.console.View;
import upm.app.data.modelos.Match;
import upm.app.data.modelos.Rol;
import upm.app.services.MatchService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CreateMatch implements Command {
    private final View view;
    private final MatchService matchService;

    public CreateMatch(View view, MatchService matchService) {
        this.view = view;
        this.matchService = matchService;
    }

    @Override
    public String name() {
        return "create-match";
    }

    @Override
    public List<String> params() {
        return List.of("<dd-MM-yyyy HH-mm-ss>", "<dni-jugador1>", "<dni-jugador2>", "<nombre-pista>");
    }

    @Override
    public List<Rol> allowedRoles() {
        return Rol.autorized();
    }

    @Override
    public String helpMessage() {
        return "Se crea un partido";
    }

    @Override
    public void execute(String[] params) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH-mm-ss");
        Match createdMatch = this.matchService.create(LocalDateTime.parse(params[0], formatter), params[1], params[2], params[3]);
        this.view.show(createdMatch.toString());
    }
}
