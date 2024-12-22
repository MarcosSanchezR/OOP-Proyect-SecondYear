package upm.app.gui.command;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.layout.HBox;
import upm.app.data.modelos.Match;
import upm.app.data.modelos.Rol;
import upm.app.gui.fx.GraphicalUserInterfaceFX;
import upm.app.gui.fx.components.CourtComboBox;
import upm.app.gui.fx.dialogs.EntityListDialog;
import upm.app.services.CourtService;
import upm.app.services.MatchService;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ReadMatch extends AbstractCommand {
    private final MatchService matchService;
    private final CourtService courtService;

    public ReadMatch(MatchService matchService, CourtService courtService) {
        this.matchService = matchService;
        this.courtService = courtService;
    }

    @Override
    public String name() {
        return "read-match";
    }

    @Override
    public List<String> params() {
        return List.of("<dd-MM-yyyy HH-mm-ss>", "<nombre-pista>");
    }

    @Override
    public List<Rol> allowedRoles() {
        return Rol.autorized();
    }

    @Override
    public String helpMessage() {
        return "Muestra la puntuacion del partido";
    }

    @Override
    public void execute() {
        ObservableList<Node> contentArea = GraphicalUserInterfaceFX.getInstance().getContentArea().getChildren();
        contentArea.clear();

        DatePicker dateSelector = new DatePicker();
        CourtComboBox courtComboBox = new CourtComboBox(courtService);
        Spinner<Integer> hourSpinner = new Spinner<>(0, 23, 9);
        hourSpinner.setEditable(true);
        Spinner<Integer> minuteSpinner = new Spinner<>(0, 59, 0, 5);
        minuteSpinner.setEditable(true);
        HBox hour = new HBox(10, new Label("Hora:"), hourSpinner, new Label("Minutos:"), minuteSpinner);

        Button submit = new Button("Leer partido");

        contentArea.addAll(dateSelector, hour, courtComboBox, submit);


        submit.setOnAction(actionEvent -> {
            LocalTime time = LocalTime.of(hourSpinner.getValue(), minuteSpinner.getValue(), 0);
            LocalDateTime localDateTime = dateSelector.getValue().atTime(time);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH-mm-ss");
            String formattedDateTime = localDateTime.format(formatter);
            List<String> values = List.of(
                    formattedDateTime,
                    courtComboBox.getValue().getKey()
            );
            this.submitActionHandler(values).handle(actionEvent);
        });
    }

    @Override
    public void executeAction(List<String> fields) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH-mm-ss");
        Match match = matchService.readMatch(LocalDateTime.parse(fields.get(0), formatter), fields.get(1));
        GraphicalUserInterfaceFX.getInstance().getStatus().successful("Consulta realizada");
        new EntityListDialog(this.name(), List.of(match));
    }
}
