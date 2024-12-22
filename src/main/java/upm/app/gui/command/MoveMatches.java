package upm.app.gui.command;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import upm.app.data.modelos.Rol;
import upm.app.gui.fx.GraphicalUserInterfaceFX;
import upm.app.gui.fx.components.DateSelector;
import upm.app.services.MatchService;

import java.time.LocalDate;
import java.util.List;

public class MoveMatches extends AbstractCommand {
    private final MatchService matchService;

    public MoveMatches(MatchService matchService) {
        this.matchService = matchService;
    }

    @Override
    public String name() {
        return "move-matches";
    }

    @Override
    public List<String> params() {
        return List.of("<aaaa-mm-dd>");
    }

    @Override
    public List<Rol> allowedRoles() {
        return List.of(Rol.ADMIN, Rol.REFEREE);
    }

    @Override
    public String helpMessage() {
        return "Mueve todos los partidos de ese dia al siguiente mas proximo";
    }

    @Override
    public void execute() {
        ObservableList<Node> contentArea = GraphicalUserInterfaceFX.getInstance().getContentArea().getChildren();
        contentArea.clear();


        DateSelector holidayDatePicker = new DateSelector("Dia festivo");

        Button submit = new Button("Establecer festividad");

        contentArea.addAll(holidayDatePicker, submit);

        submit.setOnAction(actionEvent -> {
            List<String> values = List.of(
                    holidayDatePicker.getSelectedDate().toString());
            this.submitActionHandler(values).handle(actionEvent);
        });
    }

    @Override
    public void executeAction(List<String> fields) {
        matchService.moveMatchInHoliday(LocalDate.parse(fields.get(0)));
        GraphicalUserInterfaceFX.getInstance().getStatus().successful("Se han movido los partidos correctamente");
    }
}
