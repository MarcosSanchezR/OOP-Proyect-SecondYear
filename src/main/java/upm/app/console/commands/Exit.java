package upm.app.console.commands;

import upm.app.console.Command;
import upm.app.data.modelos.Rol;

import java.util.List;

public class Exit implements Command {

    @Override
    public String name() {
        return "exit";
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
        return "Termina la ejecucion";
    }

    @Override
    public void execute(String[] params) {
        //never executed
    }
}
