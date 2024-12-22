package upm.app.gui.command;

import upm.app.data.modelos.Rol;
import upm.app.gui.fx.GraphicalUserInterfaceFX;
import upm.app.gui.fx.dialogs.EntityListDialog;
import upm.app.services.UserService;

import java.util.List;

public class ListUser extends AbstractCommand {
    private final UserService userService;

    public ListUser(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String name() {
        return "list-user";
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
        return "Muestra todos los usuarios";
    }

    @Override
    public void execute() {
        new EntityListDialog(this.name(), this.userService.listAll()
                .map(Object.class::cast).toList());
        GraphicalUserInterfaceFX.getInstance().getStatus().successful("Consulta realizada");
    }
}
