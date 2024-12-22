package upm.app.gui.command;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import upm.app.data.modelos.Rol;
import upm.app.gui.fx.GraphicalUserInterfaceFX;
import upm.app.gui.fx.components.CourtComboBox;
import upm.app.services.CourtService;
import upm.app.services.MatchService;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ScoreMatch extends AbstractCommand {
    private final MatchService matchService;
    private final CourtService courtService;

    public ScoreMatch(MatchService matchService, CourtService courtService) {
        this.matchService = matchService;
        this.courtService = courtService;
    }

    @Override
    public String name() {
        return "score-match";
    }

    @Override
    public List<String> params() {
        return List.of("<dd-MM-yyyy HH-mm-ss>", "<nombre-pista>", "<service/rest>");
    }

    @Override
    public List<Rol> allowedRoles() {
        return List.of(Rol.ADMIN, Rol.REFEREE);
    }

    @Override
    public String helpMessage() {
        return "Puntua al jugador que sirve o al jugador que resta";
    }

    @Override
    public void execute() {
        ObservableList<Node> contentArea = GraphicalUserInterfaceFX.getInstance().getContentArea().getChildren();
        contentArea.clear();

        Label dateLabel = new Label("Fecha:");
        DatePicker dateSelector = new DatePicker();
        VBox dateBox = new VBox(5, dateLabel, dateSelector);
        CourtComboBox courtComboBox = new CourtComboBox(courtService);
        Spinner<Integer> hourSpinner = new Spinner<>(0, 23, 9);
        hourSpinner.setEditable(true);
        Spinner<Integer> minuteSpinner = new Spinner<>(0, 59, 0, 5);
        minuteSpinner.setEditable(true);
        HBox hour = new HBox(10, new Label("Hora:"), hourSpinner, new Label("Minutos:"), minuteSpinner);
        ToggleGroup scoreGroup = new ToggleGroup();
        RadioButton serviceRadio = new RadioButton("Servicio");
        serviceRadio.setToggleGroup(scoreGroup);
        RadioButton restRadio = new RadioButton("Resto");
        restRadio.setToggleGroup(scoreGroup);
        VBox toggleBox = new VBox(10, serviceRadio, restRadio);


        Button submit = new Button("Puntuar");

        contentArea.addAll(dateBox, hour, courtComboBox, toggleBox, submit);

        submit.disableProperty().bind(scoreGroup.selectedToggleProperty().isNull());

        submit.setOnAction(actionEvent -> {
            LocalTime time = LocalTime.of(hourSpinner.getValue(), minuteSpinner.getValue(), 0);
            LocalDateTime localDateTime = dateSelector.getValue().atTime(time);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH-mm-ss");
            String formattedDateTime = localDateTime.format(formatter);
            String serviceRest = "";
            if (((RadioButton) scoreGroup.getSelectedToggle()).getText().equals("Servicio")) {
                serviceRest = "service";
            } else {
                serviceRest = "rest";
            }
            List<String> values = List.of(
                    formattedDateTime,
                    courtComboBox.getValue().getKey(),
                    serviceRest
            );
            this.submitActionHandler(values).handle(actionEvent);
        });

    }

    @Override
    public void executeAction(List<String> fields) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH-mm-ss");
        matchService.scoreMatch(LocalDateTime.parse(fields.get(0), formatter), fields.get(1), fields.get(2));
        GraphicalUserInterfaceFX.getInstance().getStatus().successful("Se ha puntuado correctamente");
    }
}
