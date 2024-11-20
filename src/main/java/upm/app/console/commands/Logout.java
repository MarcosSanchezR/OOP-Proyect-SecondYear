package upm.app.console.commands;

import upm.app.console.Command;
import upm.app.console.CommandLineInterface;
import upm.app.data.modelos.Rol;

import java.util.List;

public class Logout implements Command {
    private final CommandLineInterface commandLineInterface;

    public Logout(CommandLineInterface commandLineInterface) {
        this.commandLineInterface = commandLineInterface;
    }

    @Override
    public String name() {
        return "logout";
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
        return "Cierra sesion";
    }

    @Override
    public void execute(String[] params) {
    this.commandLineInterface.setUser(null);
    }
}
