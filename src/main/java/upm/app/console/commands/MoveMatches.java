package upm.app.console.commands;

import upm.app.console.Command;
import upm.app.console.View;
import upm.app.data.modelos.Rol;
import upm.app.services.MatchService;

import java.time.LocalDate;
import java.util.List;

public class MoveMatches implements Command {
    private final View view;
    private final MatchService matchService;

    public MoveMatches(View view, MatchService matchService) {
        this.view = view;
        this.matchService = matchService;
    }

    @Override
    public String name() {
        return "move-matches";
    }

    @Override
    public List<String> params() {
        return List.of("<yyyy-MM-dd>");
    }

    @Override
    public List<Rol> allowedRoles() {
        return List.of(Rol.ADMIN, Rol.REFEREE);
    }

    @Override
    public String helpMessage() {
        return "Mueve todos los partidos de ese dia al siguiente mas proximo";
    }

    @Override
    public void execute(String[] params) {
        this.matchService.moveMatchInHoliday(LocalDate.parse(params[0]));
        this.view.show("Se han movido los partidos correctamente");
    }
}
