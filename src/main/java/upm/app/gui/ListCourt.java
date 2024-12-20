package upm.app.gui;

import upm.app.data.modelos.Rol;
import upm.app.gui.command.AbstractCommand;
import upm.app.gui.fx.GraphicalUserInterfaceFX;
import upm.app.gui.fx.dialogs.EntityListDialog;
import upm.app.services.CourtService;

import java.util.List;

public class ListCourt extends AbstractCommand {
    private final CourtService courtService;

    public ListCourt(CourtService courtService) {
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
    public void execute() {
        new EntityListDialog(this.name(), this.courtService.listAll()
                .map(Object.class::cast).toList());
        GraphicalUserInterfaceFX.getInstance().getStatus().successful("Consulta realizada");
    }
}
