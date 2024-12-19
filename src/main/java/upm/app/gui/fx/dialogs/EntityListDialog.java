package upm.app.gui.fx.dialogs;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import upm.app.gui.fx.components.EntityTableView;

import java.util.List;

public class EntityListDialog extends Alert {
    public EntityListDialog(String header, List<Object> content) {
        super(AlertType.INFORMATION);
        this.setTitle("Information");
        this.setHeaderText(header);
        if (content == null || content.isEmpty()) {
            this.getDialogPane().setContent(new Label("No existen resultados!!!"));
        } else {
            EntityTableView entityTableView = new EntityTableView(content);
            this.getDialogPane().setContent(entityTableView);
        }
        this.showAndWait();
    }
}

