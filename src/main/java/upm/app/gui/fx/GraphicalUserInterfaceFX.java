package upm.app.gui.fx;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import upm.app.DependencyInjector;
import upm.app.data.modelos.Rol;
import upm.app.gui.Controller;
import upm.app.gui.fx.components.Status;

public class GraphicalUserInterfaceFX extends Application {
    private static GraphicalUserInterfaceFX instance;
    private Controller controller;
    private Menu commandMenu;
    private MenuItem logoutItem;
    private MenuItem loginItem;
    private VBox contentArea;
    private Status status;

    public static void main(String[] args) {
        Application.launch(args);
    }

    public static GraphicalUserInterfaceFX getInstance() {
        return instance;
    }

    private static void setInstance(GraphicalUserInterfaceFX graphicalUserInterfaceFX) {
        instance = graphicalUserInterfaceFX;
    }

    @Override
    public void init() {
        this.controller = DependencyInjector.getInstance().getController();
        GraphicalUserInterfaceFX.setInstance(this);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Tennis App");

        BorderPane root = new BorderPane();

        MenuBar menuBar;
        menuBar = new MenuBar();
        this.commandMenu = new Menu("Commands");
        menuBar.getMenus().addAll(this.prepareFileMenu(primaryStage), commandMenu, this.prepareHelpMenu());

        HBox topBox = this.prepareTop(menuBar);
        root.setTop(topBox);

        this.contentArea = new VBox();
        contentArea.setPadding(new Insets(10));
        contentArea.setSpacing(10);
        root.setCenter(contentArea);

        this.status = new Status();
        root.setBottom(status);

        this.generatedCommandMenu();

        Scene scene = new Scene(root, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private HBox prepareTop(MenuBar menuBar) {
        Label userLabel = new Label(this.controller.userName());
        this.controller.setListenerToUser((String userName) -> {
            userLabel.setText(userName);
            generatedCommandMenu();
        });
        userLabel.setAlignment(Pos.CENTER_RIGHT);
        userLabel.setPadding(new Insets(10));
        userLabel.setFont(Font.font("Arial", FontWeight.BOLD, 10));
        HBox topBox = new HBox(menuBar, userLabel);
        topBox.setAlignment(Pos.CENTER_LEFT);
        topBox.setSpacing(10);
        HBox.setHgrow(menuBar, Priority.ALWAYS);
        topBox.setAlignment(Pos.CENTER);
        return topBox;
    }

    private Menu prepareFileMenu(Stage primaryStage) {
        Menu fileMenu = new Menu("File");
        loginItem = new MenuItem(Controller.LOGIN);
        loginItem.setOnAction(event -> this.controller.command(Controller.LOGIN).execute());
        fileMenu.getItems().add(loginItem);
        logoutItem = new MenuItem(Controller.LOGOUT);
        logoutItem.setOnAction(event -> this.controller.command(Controller.LOGOUT).execute());
        fileMenu.getItems().add(logoutItem);
        MenuItem exitItem = new MenuItem("exit");
        exitItem.setOnAction(event -> primaryStage.close());
        fileMenu.getItems().add(exitItem);
        return fileMenu;
    }

    private Menu prepareHelpMenu() {
        Menu helpMenu = new Menu("Help");
        MenuItem aboutItem = new MenuItem("about");
        aboutItem.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("UPM");
            alert.setHeaderText("POO. Curso 2024-25");
            alert.showAndWait();
        });
        helpMenu.getItems().add(aboutItem);
        return helpMenu;
    }

    private void generatedCommandMenu() {
        this.commandMenu.getItems().clear();
        for (String key : this.controller.keys()) {
            MenuItem menuItem = new MenuItem(key);
            menuItem.setOnAction(this.menuActionHandler(key));
            this.commandMenu.getItems().addAll(menuItem);
        }
        this.loginItem.setDisable(!Rol.NONE.equals(this.controller.userRol()));
        this.logoutItem.setDisable(Rol.NONE.equals(this.controller.userRol()));
    }

    private EventHandler<ActionEvent> menuActionHandler(String item) {
        return eventSubmit -> this.controller.command(item).execute();
    }

    public VBox getContentArea() {
        return this.contentArea;
    }

    public Status getStatus() {
        return this.status;
    }
}

