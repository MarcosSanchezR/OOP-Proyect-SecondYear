package upm.app.console.commands;

import upm.app.console.Command;
import upm.app.console.View;
import upm.app.data.modelos.Rol;
import upm.app.data.modelos.TennisCourt;
import upm.app.services.CourtService;

import java.util.List;

public class CreateCourt implements Command {
    private final View view;
    private final CourtService courtService;

    public CreateCourt(View view, CourtService courtService) {
        this.view = view;
        this.courtService = courtService;
    }


    @Override
    public String name() {
        return "create-court";
    }

    @Override
    public List<String> params() {
        return List.of("<nombre>", "<superficie>", "<localizacion>");
    }

    @Override
    public List<Rol> allowedRoles() {
        return List.of(Rol.ADMIN);
    }

    @Override
    public String helpMessage() {
        return "Se crea una pista de tenis";
    }

    @Override
    public void execute(String[] params) {
        TennisCourt createdCourt = this.courtService.create(new TennisCourt(params[0], params[1], params[2]));
        this.view.show(createdCourt.toString());
    }
}
