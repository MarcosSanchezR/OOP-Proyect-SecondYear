package upm.app.gui.fx.components;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;

public class RequiredTextField extends HBox {

    public static final String CSS_BOLD = "-fx-font-weight: bold; ";
    public static final String CSS_BLACK = "-fx-text-fill: black; ";
    public static final String CSS_RED = "-fx-text-fill: red;";
    private final TextField textField;
    private final Label nameLabel;

    private final int minLength;

    public RequiredTextField(String name, int minLength) {
        super(5);
        if (minLength < 0) {
            throw new IllegalArgumentException("Minimum length cannot be negative.");
        }
        this.minLength = minLength;

        this.nameLabel = new Label(name + ":");
        this.nameLabel.setStyle(CSS_BOLD + CSS_BLACK);
        this.nameLabel.setPrefWidth(100);

        this.textField = new TextField();
        this.textField.setPromptText("Enter " + name);
        this.textField.setTooltip(new Tooltip("Minimum length" + this.minLength));

        this.getChildren().addAll(nameLabel, textField);

        textField.textProperty().addListener((obs, oldText, newText) -> {
            if (this.isInvalid()) {
                textField.setStyle(CSS_RED);
            } else {
                textField.setStyle(CSS_BLACK);
            }
        });
    }

    private boolean isInvalid() {
        return this.textField.getText() == null || this.textField.getText().length() < minLength;
    }

    public BooleanBinding observableInvalid() {
        return Bindings.createBooleanBinding(
                this::isInvalid,
                this.textField.textProperty()
        );
    }

    public String getText() {
        return this.textField.getText();
    }

    public void setText(String text) {
        textField.setText(text);
    }

    public int getMinLength() {
        return this.minLength;
    }

    public String getName() {
        return this.nameLabel.getText();
    }
}

