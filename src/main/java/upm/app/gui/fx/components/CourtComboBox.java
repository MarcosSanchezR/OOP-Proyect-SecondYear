package upm.app.gui.fx.components;

import javafx.beans.binding.BooleanBinding;
import javafx.scene.control.ComboBox;
import upm.app.services.CourtService;

import java.util.List;

public class CourtComboBox extends ComboBox<KeyValue<String>> {

    public CourtComboBox(CourtService courtService) {
        // Obtener las pistas desde el servicio y mapearlas a KeyValue
        List<KeyValue<String>> keyValues = courtService.listAll().stream()
                .map(court -> new KeyValue<>(court.getName(), court.getSurfaceType() + " - " + court.getLocation()))
                .toList();

        // Agregar las pistas al ComboBox
        this.getItems().addAll(keyValues);
        this.setPromptText("Selecciona una pista de tenis");
    }

    // Método para observar si no se ha seleccionado ninguna pista
    public BooleanBinding observableNotSelect() {
        return this.valueProperty().isNull();
    }
}