package upm.app.gui.command;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import upm.app.data.modelos.Rol;
import upm.app.gui.fx.GraphicalUserInterfaceFX;
import upm.app.gui.fx.components.CourtComboBox;
import upm.app.services.CourtService;

import java.util.List;

public class DeleteCourt extends AbstractCommand {
    private final CourtService courtService;

    public DeleteCourt(CourtService courtService) {
        this.courtService = courtService;
    }

    @Override
    public String name() {
        return "delete-court";
    }

    @Override
    public List<String> params() {
        return List.of("<nombre>");
    }

    @Override
    public List<Rol> allowedRoles() {
        return List.of(Rol.ADMIN);
    }

    @Override
    public String helpMessage() {
        return "Borra la pista a traves del nombre";
    }

    @Override
    public void execute() {
        ObservableList<Node> contentArea = GraphicalUserInterfaceFX.getInstance().getContentArea().getChildren();
        contentArea.clear();

        CourtComboBox courtComboBox = new CourtComboBox(courtService);

        Button submit = new Button("Eliminar");

        contentArea.addAll(courtComboBox, submit);

        submit.setOnAction(actionEvent -> {
            String selectedCourt = courtComboBox.getValue().toString();
            this.submitActionHandler(List.of(selectedCourt)).handle(actionEvent);
        });
    }

    @Override
    public void executeAction(List<String> fields) {
        courtService.deleteByName(fields.get(0));
        GraphicalUserInterfaceFX.getInstance().getStatus().successful("Pista borrada");
    }
}
