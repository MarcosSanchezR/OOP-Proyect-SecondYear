package upm.app.gui.command;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import upm.app.data.modelos.Rol;
import upm.app.gui.fx.GraphicalUserInterfaceFX;
import upm.app.gui.fx.components.UserComboBox;
import upm.app.services.UserService;

import java.util.List;

public class DeleteUser extends AbstractCommand{
    private final UserService userService;

    public DeleteUser(UserService userService) {
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
    public void execute() {
        ObservableList<Node> contentArea = GraphicalUserInterfaceFX.getInstance().getContentArea().getChildren();
        contentArea.clear();

        UserComboBox userComboBox = new UserComboBox(userService, "Dni");

        Button submit= new Button("Eliminar");

        contentArea.addAll(userComboBox, submit);

        submit.setOnAction(actionEvent -> {
            String selectedDni = userComboBox.getSelectedDni();
            this.submitActionHandler(List.of(selectedDni)).handle(actionEvent);
        });
    }

    @Override
    public void executeAction(List<String> fields) {
        userService.deleteByDni(fields.get(0));
        GraphicalUserInterfaceFX.getInstance().getStatus().successful("Usuario eliminado");
    }
}
