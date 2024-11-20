package upm.app.console.commands;

import upm.app.console.Command;
import upm.app.console.View;
import upm.app.data.modelos.Rol;
import upm.app.services.MatchService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class EstablishWinner implements Command {
private final View view;
private final MatchService matchService;

    public EstablishWinner(View view, MatchService matchService) {
        this.view = view;
        this.matchService = matchService;
    }

    @Override
    public String name() {
        return "establish-winner";
    }

    @Override
    public List<String> params() {
        return List.of("<dd-MM-yyyy HH-mm-ss> (inicio)", "<nombre-pista>", "<dni-ganador>");
    }

    @Override
    public List<Rol> allowedRoles() {
        return List.of(Rol.ADMIN, Rol.REFEREE);
    }

    @Override
    public String helpMessage() {
        return "Establece un ganador al partido";
    }

    @Override
    public void execute(String[] params) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH-mm-ss");
        this.matchService.establishWinner(LocalDateTime.parse(params[0], formatter), params[1], params[2]);
        this.view.show("Ganador establecido");
    }
}
