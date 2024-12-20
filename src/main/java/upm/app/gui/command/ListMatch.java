package upm.app.gui.command;

import upm.app.data.modelos.Match;
import upm.app.data.modelos.Rol;
import upm.app.gui.fx.GraphicalUserInterfaceFX;
import upm.app.gui.fx.dialogs.EntityListDialog;
import upm.app.services.MatchService;

import java.util.List;

public class ListMatch extends AbstractCommand{
    private final MatchService matchService;

    public ListMatch(MatchService matchService) {
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
    public void execute() {
        new EntityListDialog(this.name(), this.matchService.listAll()
                .map(Object.class::cast).toList());
        GraphicalUserInterfaceFX.getInstance().getStatus().successful("Consulta realizada");
    }
}
