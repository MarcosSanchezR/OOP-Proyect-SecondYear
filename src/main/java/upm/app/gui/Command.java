package upm.app.gui;

import upm.app.data.modelos.Rol;

import java.util.List;

public interface Command {
    String name();

    List<String> params();

    List<Rol> allowedRoles();

    String helpMessage();

    void execute();
}
