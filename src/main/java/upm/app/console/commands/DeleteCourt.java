package upm.app.console.commands;

import upm.app.console.Command;
import upm.app.console.View;
import upm.app.data.modelos.Rol;
import upm.app.services.CourtService;

import java.util.List;

public class DeleteCourt implements Command {
    private final View view;
    private final CourtService courtService;

    public DeleteCourt(View view, CourtService courtService) {
        this.view = view;
        this.courtService = courtService;
    }


    @Override
    public String name() {
        return "delete-court";
    }

    @Override
    public List<String> params() {
        return List.of("<nombre>");
    }

    @Override
    public List<Rol> allowedRoles() {
        return List.of(Rol.ADMIN);
    }

    @Override
    public String helpMessage() {
        return "Borra la pista a traves del nombre";
    }

    @Override
    public void execute(String[] params) {
        this.courtService.deleteByName(params[0]);
        this.view.show("Pista borrada");
    }
}
