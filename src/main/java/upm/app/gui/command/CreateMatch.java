package upm.app.gui.command;

import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.layout.HBox;
import upm.app.data.modelos.Match;
import upm.app.data.modelos.Rol;
import upm.app.gui.fx.GraphicalUserInterfaceFX;
import upm.app.gui.fx.components.CourtComboBox;
import upm.app.gui.fx.components.DateSelector;
import upm.app.gui.fx.components.UserComboBox;
import upm.app.gui.fx.dialogs.EntityListDialog;
import upm.app.services.CourtService;
import upm.app.services.MatchService;
import upm.app.services.UserService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CreateMatch extends AbstractCommand{
    private final MatchService matchService;
    private final UserService userService;
    private final CourtService courtService;

    public CreateMatch(MatchService matchService, UserService userService, CourtService courtService) {
        this.matchService = matchService;
        this.userService = userService;
        this.courtService = courtService;
    }

    @Override
    public String name() {
        return "create-match";
    }

    @Override
    public List<String> params() {
        return List.of("<dd-MM-yyyy HH-mm-ss>", "<dni-jugador1>", "<dni-jugador2>", "<nombre-pista>");
    }

    @Override
    public List<Rol> allowedRoles() {
        return Rol.autorized();
    }

    @Override
    public String helpMessage() {
        return "Se crea un partido";
    }

    @Override
    public void execute() {
        ObservableList<Node> contentArea = GraphicalUserInterfaceFX.getInstance().getContentArea().getChildren();
        contentArea.clear();

        DateSelector dateSelector=new DateSelector("Fecha del partido");
        UserComboBox user1 = new UserComboBox(userService, "Jugador 1");
        UserComboBox user2 = new UserComboBox(userService, "Jugador 2");
        CourtComboBox courtComboBox = new CourtComboBox(courtService);
        Spinner<Integer> hourSpinner = new Spinner<>(0, 23, 9);
        hourSpinner.setEditable(true);
        Spinner<Integer> minuteSpinner = new Spinner<>(0, 59, 0, 5);
        minuteSpinner.setEditable(true);
        HBox hour= new HBox(10, new Label("Hora:"), hourSpinner, new Label("Minutos:"), minuteSpinner);

        Button submit = new Button("Crear partido");

        contentArea.addAll(dateSelector, hour, user1, user2, courtComboBox, submit);

        submit.disableProperty().bind(
                Bindings.or(
                        user1.observableNotSelect(),
                        user2.observableNotSelect()
                ).or(courtComboBox.observableNotSelect()).or(Bindings.createBooleanBinding(
                        () -> dateSelector.getSelectedDate() == null,
                        dateSelector.getDatePicker().valueProperty()))
        );

        submit.setOnAction(actionEvent -> {
            List<String> values = List.of(
                    dateSelector.getSelectedDate().toString(),
                    hourSpinner.getValue() + "-" + minuteSpinner.getValue(),
                    user1.getSelectedDni(),
                    user2.getSelectedDni(),
                    courtComboBox.getValue().getKey()
            );
            this.submitActionHandler(values).handle(actionEvent);
        });
    }

    @Override
    public void executeAction(List<String> fields) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH-mm-ss");
        Match createdMatch = matchService.create(
                LocalDateTime.parse(fields.get(0), formatter), fields.get(1), fields.get(2), fields.get(3));
        GraphicalUserInterfaceFX.getInstance().getStatus().successful("Partido creado correctamente");
        new EntityListDialog(this.name(), List.of(createdMatch));
    }
}
