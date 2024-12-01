package upm.app.console.commands;

import upm.app.console.Command;
import upm.app.console.CommandLineInterface;
import upm.app.data.modelos.Rol;
import upm.app.data.modelos.User;
import upm.app.services.UserService;

import java.util.List;

public class Login implements Command {
    private final UserService userService;
    private final CommandLineInterface commandLineInterface;

    public Login(UserService userService, CommandLineInterface commandLineInterface) {
        this.userService = userService;
        this.commandLineInterface = commandLineInterface;
    }

    @Override
    public String name() {
        return "login";
    }

    @Override
    public List<String> params() {
        return List.of("<dni>", "<password>");
    }

    @Override
    public List<Rol> allowedRoles() {
        return Rol.all();
    }

    @Override
    public String helpMessage() {
        return "Inicia sesion";
    }

    @Override
    public void execute(String[] params) {
        User logged = this.userService.login(params[0], params[1]);
        this.commandLineInterface.setUser(logged);
    }
}
