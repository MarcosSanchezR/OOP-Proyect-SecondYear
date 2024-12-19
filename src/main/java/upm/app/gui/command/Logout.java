package upm.app.gui.command;

import upm.app.data.modelos.Rol;
import upm.app.gui.Controller;
import upm.app.gui.fx.GraphicalUserInterfaceFX;

import java.util.List;

public class Logout extends AbstractCommand {
    private final Controller controller;

    public Logout(Controller controller) {
        this.controller = controller;
    }

    @Override
    public String name() {
        return Controller.LOGOUT;
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
    public void execute() {
        this.controller.setUser(null);
        GraphicalUserInterfaceFX.getInstance().getStatus().successful("Hasta pronto!");
    }
}
