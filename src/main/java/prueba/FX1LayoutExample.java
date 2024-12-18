package prueba;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class FX1LayoutExample extends Application {

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Ejemplo de Layouts en JavaFX");

        // HBox (Horizontal Layout)
        HBox hbox = new HBox(10);
        hbox.getChildren().addAll(new Button("Botón 1"), new Button("Botón 2"), new Button("Botón 3"));

        // VBox (Vertical Layout)
        VBox vbox = new VBox(10);
        vbox.getChildren().addAll(new Label("Etiqueta 1"), new Label("Etiqueta 2"), new Label("Etiqueta 3"));

        // BorderPane (Border Layout)
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(new Label("Top"));
        borderPane.setBottom(new Label("Bottom"));
        borderPane.setLeft(new Button("Left"));
        borderPane.setRight(new Button("Right"));
        borderPane.setCenter(new Label("Center"));

        // GridPane (Grid Layout)
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.add(new Label("Etiqueta 1,1"), 0, 0);
        gridPane.add(new Label("Etiqueta 2,1"), 0, 1);
        gridPane.add(new Button("Botón 1,2"), 1, 0);
        gridPane.add(new Button("Botón 2,2"), 1, 1);

        // StackPane (Overlapping Layout)
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(new Label("Etiqueta en el fondo"), new Button("Botón encima"));

        // Root layout to combine everything
        VBox root = new VBox(20);
        root.getChildren().addAll(
                new Label("Ejemplo de HBox:"),
                hbox,
                new Label("Ejemplo de VBox:"),
                vbox,
                new Label("Ejemplo de BorderPane:"),
                borderPane,
                new Label("Ejemplo de GridPane:"),
                gridPane,
                new Label("Ejemplo de StackPane:"),
                stackPane
        );

        // Configuración de la escena
        Scene scene = new Scene(root, 400, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
