package upm.app.gui.command;

import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import upm.app.data.modelos.Rol;
import upm.app.data.modelos.TennisCourt;
import upm.app.gui.fx.GraphicalUserInterfaceFX;
import upm.app.gui.fx.components.RequiredTextField;
import upm.app.gui.fx.components.SurfaceComboBox;
import upm.app.gui.fx.dialogs.EntityListDialog;
import upm.app.services.CourtService;

import java.util.List;

public class CreateCourt extends AbstractCommand {
    private final CourtService courtService;

    public CreateCourt(CourtService courtService) {
        this.courtService = courtService;
    }

    @Override
    public String name() {
        return "create-court";
    }

    @Override
    public List<String> params() {
        return List.of("<nombre>", "<superficie>", "<localizacion>");
    }

    @Override
    public List<Rol> allowedRoles() {
        return List.of(Rol.ADMIN, Rol.REFEREE);
    }

    @Override
    public String helpMessage() {
        return "Se crea una pista de tenis";
    }

    @Override
    public void execute() {
        ObservableList<Node> contentArea = GraphicalUserInterfaceFX.getInstance().getContentArea().getChildren();
        contentArea.clear();

        TextField nameField = new TextField();
        nameField.setPromptText("Nombre");
        SurfaceComboBox surfacePicker = new SurfaceComboBox();
        RequiredTextField locationField = new RequiredTextField("Localizacion", 4);

        Button submit = new Button("Crear Pista");

        contentArea.addAll(nameField, surfacePicker, locationField, submit);

        submit.disableProperty().bind(
                Bindings.or(
                        locationField.observableInvalid(),
                        Bindings.createBooleanBinding(
                                () -> nameField.getText().isEmpty() ||
                                        surfacePicker.getSelectionModel().getSelectedItem() ==null,
                                nameField.textProperty(),
                                surfacePicker.getSelectionModel().selectedItemProperty()
                        )
                )
        );

        submit.setOnAction(actionEvent -> {
            List<String> values = List.of(
                    nameField.getText(),
                    locationField.getText(),
                    surfacePicker.getSelectionModel().getSelectedItem());
            this.submitActionHandler(values).handle(actionEvent);
        });

    }

    @Override
    public void executeAction(List<String> fields) {
        TennisCourt createdCourt =this.courtService.create(
                new TennisCourt(fields.get(0), fields.get(1), fields.get(2)));
        GraphicalUserInterfaceFX.getInstance().getStatus().successful("Pista creada correctamente");
        new EntityListDialog(this.name(), List.of(createdCourt));
    }
}
