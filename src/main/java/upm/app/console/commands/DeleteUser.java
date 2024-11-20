package upm.app.console.commands;

import upm.app.console.Command;
import upm.app.console.View;
import upm.app.data.modelos.Rol;
import upm.app.services.UserService;

import java.util.List;

public class DeleteUser implements Command {
    private final View view;
    private final UserService userService;

    public DeleteUser(View view, UserService userService) {
        this.view = view;
        this.userService = userService;
    }

    @Override
    public String name() {
        return "delete-user";
    }

    @Override
    public List<String> params() {
        return List.of("<dni>");
    }

    @Override
    public List<Rol> allowedRoles() {
        return List.of(Rol.ADMIN);
    }

    @Override
    public String helpMessage() {
        return "Borra el usuario a traves del dni";
    }

    @Override
    public void execute(String[] params) {
        this.userService.deleteByDni(params[0]);
        this.view.show("Usuario borrado");
    }
}
