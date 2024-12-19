package upm.app.gui.fx.components;

import javafx.beans.binding.BooleanBinding;
import javafx.scene.control.ComboBox;

public class SurfaceComboBox extends ComboBox<String> {
    public SurfaceComboBox() {
        this.getItems().addAll("arcilla", "cesped", "dura");
        this.setPromptText("Selecciona un tipo de superficie");
    }

    public BooleanBinding observableNotSelect() {
        return this.valueProperty().isNull();
    }
}

