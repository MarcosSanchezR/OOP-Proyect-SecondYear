package upm.app.console.commands;

import upm.app.console.Command;
import upm.app.console.View;
import upm.app.data.modelos.Rol;
import upm.app.services.MatchService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ReadMatch implements Command {
    private final View view;
    private final MatchService matchService;

    public ReadMatch(View view, MatchService matchService) {
        this.view = view;
        this.matchService = matchService;
    }

    @Override
    public String name() {
        return "read-match";
    }

    @Override
    public List<String> params() {
        return List.of("<dd-MM-yyyy HH-mm-ss>", "<nombre-pista>");
    }

    @Override
    public List<Rol> allowedRoles() {
        return Rol.autorized();
    }

    @Override
    public String helpMessage() {
        return "Muestra la puntuacion del partido";
    }

    @Override
    public void execute(String[] params) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH-mm-ss");
        this.view.show(this.matchService.readMatch(LocalDateTime.parse(params[0], formatter), params[1]).scoreboard());
    }
}
