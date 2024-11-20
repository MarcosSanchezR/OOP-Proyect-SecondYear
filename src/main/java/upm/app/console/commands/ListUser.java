package upm.app.console.commands;

import upm.app.console.Command;
import upm.app.console.View;
import upm.app.data.modelos.Rol;
import upm.app.data.modelos.User;
import upm.app.services.UserService;

import java.util.List;

public class ListUser implements Command {
    private final View view;
    private final UserService userService;

    public ListUser(View view, UserService userService) {
        this.view = view;
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
        return "Muestra  todos los usuarios";
    }

    @Override
    public void execute(String[] params) {
        this.userService.listAll().forEach(user -> this.view.show(user.toString()));
    }
}
