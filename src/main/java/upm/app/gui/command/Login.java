package upm.app.gui.command;

import upm.app.data.modelos.Rol;
import upm.app.data.modelos.User;
import upm.app.gui.Controller;
import upm.app.gui.fx.GraphicalUserInterfaceFX;
import upm.app.services.UserService;

import java.util.List;

public class Login extends AbstractCommand {
    private final UserService userService;
    private final Controller controller;

    public Login(UserService userService, Controller controller) {
        this.userService = userService;
        this.controller = controller;
    }

    @Override
    public String name() {
        return Controller.LOGIN;
    }

    @Override
    public List<String> params() {
        return List.of("<dni>", "<password>");
    }

    @Override
    public List<Rol> allowedRoles() {
        return List.of(Rol.NONE);
    }

    @Override
    public String helpMessage() {
        return "Inicia sesion";
    }

    @Override
    public void execute() {
        this.preparedForm();
    }

    @Override
    public void executeAction(List<String> fields) {
        User userLogged = this.userService.login(fields.get(0), fields.get(1));
        this.controller.setUser(userLogged);
        GraphicalUserInterfaceFX.getInstance().getStatus().successful("Bienvenido, " + userLogged.getName());
    }
}
