package upm.app.gui.command;

import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import upm.app.data.modelos.Rol;
import upm.app.data.modelos.User;
import upm.app.gui.fx.GraphicalUserInterfaceFX;
import upm.app.gui.fx.components.DateSelector;
import upm.app.gui.fx.components.RequiredTextField;
import upm.app.gui.fx.dialogs.EntityListDialog;
import upm.app.services.UserService;

import java.time.LocalDate;
import java.util.List;

public class CreateUser extends AbstractCommand {
    private final UserService userService;

    public CreateUser(UserService userService) {
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
    public void execute() {
        ObservableList<Node> contentArea = GraphicalUserInterfaceFX.getInstance().getContentArea().getChildren();
        contentArea.clear();

        Label nameLabel = new Label("Username:");
        TextField nameField = new TextField();
        nameField.setPromptText("Nombre");
        nameField.setPrefWidth(100);
        VBox nameBox = new VBox(5, nameLabel, nameField);
        RequiredTextField dniField = new RequiredTextField("DNI", 9);
        DateSelector birthDatePicker = new DateSelector("Fecha de Nacimiento:");
        Label passLabel = new Label("Contraseña:");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Contraseña");
        passwordField.setPrefWidth(100);
        VBox passBox = new VBox(5, passLabel, passwordField);

        Button submit = new Button("Crear Usuario");

        contentArea.addAll(nameBox, dniField, birthDatePicker, passBox, submit);

        submit.disableProperty().bind(
                Bindings.or(
                        dniField.observableInvalid(),
                        Bindings.createBooleanBinding(
                                () -> nameField.getText().isEmpty() ||
                                        birthDatePicker.getSelectedDate() == null ||
                                        passwordField.getText().isEmpty(),
                                nameField.textProperty(),
                                birthDatePicker.getDatePicker().valueProperty(),
                                passwordField.textProperty()
                        )
                )
        );

        submit.setOnAction(actionEvent -> {
            List<String> values = List.of(
                    nameField.getText(),
                    birthDatePicker.getSelectedDate().toString(),
                    dniField.getText(),
                    passwordField.getText());
            this.submitActionHandler(values).handle(actionEvent);
        });
    }

    @Override
    public void executeAction(List<String> fields) {
        User createdUser = this.userService.create(
                new User(fields.get(0), LocalDate.parse(fields.get(1)), fields.get(2), fields.get(3)));
        GraphicalUserInterfaceFX.getInstance().getStatus().successful("Usuario creado correctamente");
        new EntityListDialog(this.name(), List.of(createdUser));
    }
}
