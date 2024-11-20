package upm.app.console.commands;

import upm.app.console.Command;
import upm.app.console.View;
import upm.app.data.modelos.Rol;
import upm.app.services.MatchService;

import java.util.List;

public class ListMatch implements Command {
    private final View view;
    private final MatchService matchService;

    public ListMatch(View view, MatchService matchService) {
        this.view = view;
        this.matchService = matchService;
    }

    @Override
    public String name() {
        return "list-match";
    }

    @Override
    public List<String> params() {
        return List.of();
    }

    @Override
    public List<Rol> allowedRoles() {
        return Rol.all();
    }

    @Override
    public String helpMessage() {
        return "Muestra todos los partidos";
    }

    @Override
    public void execute(String[] params) {
    this.matchService.listAll().forEach(match -> this.view.show(match.toString()));
    }
}
