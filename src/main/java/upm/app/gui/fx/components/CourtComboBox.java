package upm.app.gui.fx.components;

import javafx.beans.binding.BooleanBinding;
import javafx.scene.control.ComboBox;
import upm.app.services.CourtService;

import java.util.List;

public class CourtComboBox extends ComboBox<KeyValue<String>> {

    public CourtComboBox(CourtService courtService) {
        List<KeyValue<String>> keyValues = courtService.listAll()
                .map(court -> new KeyValue<>(court.getName(), court.getSurfaceType() + " - " + court.getLocation()))
                .toList();

        this.getItems().addAll(keyValues);
        this.setPromptText("Selecciona una pista");
    }

    public BooleanBinding observableNotSelect() {
        return this.valueProperty().isNull();
    }
}