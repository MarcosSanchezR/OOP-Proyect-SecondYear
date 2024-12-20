package upm.app.gui.fx.components;


import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import upm.app.services.UserService;

import java.util.List;

public class UserComboBox extends ComboBox<String> {

    private final ObservableList<String> allItems;

    public UserComboBox(UserService userService, String text) {
        this.allItems = FXCollections.observableArrayList(
                userService.listAll()
                        .map(user -> user.getDni() + " - " + user.getName())
                        .toList()
        );

        this.setItems(FXCollections.observableArrayList(this.allItems));
        this.setEditable(true);
        this.setPromptText(text);

        TextField editor = this.getEditor();
        editor.textProperty().addListener((obs, oldText, newText) -> filterItems(newText));
    }

    private void filterItems(String filter) {
        if (filter == null || filter.isEmpty()) {
            this.setItems(FXCollections.observableArrayList(this.allItems));
        } else {
            String lowerCaseFilter = filter.toLowerCase();
            List<String> filteredItems = this.allItems.stream()
                    .filter(item -> item.toLowerCase().contains(lowerCaseFilter))
                    .toList();
            this.setItems(FXCollections.observableArrayList(filteredItems));
        }
        if (!this.isShowing()) {
            this.show();
        }
    }

    public BooleanBinding observableNotSelect() {
        return this.valueProperty().isNull();
    }

    public String getSelectedDni() {
        String selected = this.getValue();
        if (selected != null && selected.contains(" - ")) {
            return selected.split(" - ")[0];
        }
        return null;
    }
}
