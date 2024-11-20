package upm.app.console.commands;

import upm.app.console.Command;
import upm.app.console.View;
import upm.app.data.modelos.Rol;
import upm.app.data.modelos.User;
import upm.app.services.UserService;

import java.time.LocalDate;
import java.util.List;

public class CreateUser implements Command {
    private final View view;
    private final UserService userService;

    public CreateUser(View view, UserService userService) {
        this.view = view;
        this.userService = userService;
    }

    @Override
    public String name() {
        return "create-user";
    }

    @Override
    public List<String> params() {
        return List.of("<nombre>", "<aaaa-mm-dd>", "<dni>", "<contraseña>");
    }

    @Override
    public List<Rol> allowedRoles() {
        return Rol.all();
    }

    @Override
    public String helpMessage() {
        return "Se crea un usuario";
    }

    @Override
    public void execute(String[] params) {
        User createdUser = this.userService.create(new User(params[0], LocalDate.parse(params[1]), params[2], params[3]));
        this.view.show(createdUser.toString());
    }
}
