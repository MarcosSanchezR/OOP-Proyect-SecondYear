package upm.app.gui.fx.components;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class Status extends Label {

    public Status() {
        super("Status");
        this.initializeStyle();
    }

    private void initializeStyle() {
        this.info("Status");
        this.setFont(Font.font("Arial", FontWeight.BOLD, 10));
        this.setPadding(new Insets(10));
        this.setTextFill(Color.BLACK);
        this.setMaxWidth(Double.MAX_VALUE);
    }

    public void successful(String message) {
        this.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, CornerRadii.EMPTY, Insets.EMPTY)));
        this.setText("Successful! " + message);
    }

    public void info(String message) {
        this.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        this.setText(message);
    }

    public void error(String message) {
        this.setBackground(new Background(new BackgroundFill(Color.LIGHTSALMON, CornerRadii.EMPTY, Insets.EMPTY)));
        this.setText("ERROR! " + message);
    }
}
