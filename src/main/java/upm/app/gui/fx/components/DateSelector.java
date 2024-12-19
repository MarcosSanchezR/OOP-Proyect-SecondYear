package upm.app.gui.fx.components;

import javafx.geometry.Insets;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import java.time.LocalDate;

public class DateSelector extends HBox {

    private final Label label;
    private final DatePicker datePicker;

    public DateSelector(String labelText) {
        super(10); // Espaciado entre los elementos
        this.setPadding(new Insets(10)); // Espaciado interno

        // Crear la etiqueta
        this.label = new Label(labelText);
        this.label.setMinWidth(100); // Ancho mínimo para la etiqueta

        // Crear el selector de fecha
        this.datePicker = new DatePicker();
        HBox.setHgrow(this.datePicker, Priority.ALWAYS); // Permite que el DatePicker crezca

        // Agregar los elementos al layout
        this.getChildren().addAll(this.label, this.datePicker);
    }

    // Métodos para acceder a la fecha seleccionada
    public LocalDate getSelectedDate() {
        return this.datePicker.getValue();
    }

    public void setSelectedDate(LocalDate date) {
        this.datePicker.setValue(date);
    }

    public DatePicker getDatePicker() {
        return this.datePicker;
    }
}
