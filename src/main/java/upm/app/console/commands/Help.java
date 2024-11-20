package upm.app.console.commands;

import upm.app.console.Command;
import upm.app.console.CommandLineInterface;
import upm.app.data.modelos.Rol;

import java.util.List;

public class Help implements Command {
    private final CommandLineInterface commandLineInterface;

    public Help(CommandLineInterface commandLineInterface) {
        this.commandLineInterface = commandLineInterface;
    }


    @Override
    public String name() {
        return "help";
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
        return "Muestra la ayuda de los comandos";
    }

    @Override
    public void execute(String[] params) {
    this.commandLineInterface.help();
    }
}
