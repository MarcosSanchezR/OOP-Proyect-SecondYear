package upm.app.console.commands;

import upm.app.console.Command;
import upm.app.console.View;
import upm.app.data.modelos.Rol;
import upm.app.services.CourtService;

import java.util.List;

public class ListCourt implements Command {
    private final View view;
    private final CourtService courtService;

    public ListCourt(View view, CourtService courtService) {
        this.view = view;
        this.courtService = courtService;
    }


    @Override
    public String name() {
        return "list-court";
    }

    @Override
    public List<String> params() {
        return List.of();
    }

    @Override
    public List<Rol> allowedRoles() {
        return Rol.autorized();
    }

    @Override
    public String helpMessage() {
        return "Muestra todas las pistas";
    }

    @Override
    public void execute(String[] params) {
        this.courtService.listAll().forEach(court -> this.view.show(court.toString()));
    }
}
