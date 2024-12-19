package upm.app.gui.fx.components;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.apache.logging.log4j.LogManager;

import java.lang.reflect.Method;
import java.util.List;

public class EntityTableView extends TableView<Object> {

    private static final String IS = "is";
    private static final String GET = "get";

    public EntityTableView(List<Object> content) {
        if (!content.isEmpty()) {
            ObservableList<Object> data = FXCollections.observableArrayList(content);
            this.addColumns(content.get(0).getClass().getSuperclass());
            this.addColumns(content.get(0).getClass());
            this.setItems(data);
        }
    }

    private void addColumns(Class<?> clazz) {
        if (clazz == null) {
            return;
        }

        for (Method method : clazz.getDeclaredMethods()) {
            if (isGetter(method)) {
                String fieldName = extractFieldName(method);
                TableColumn<Object, String> column = new TableColumn<>(fieldName);
                column.setCellValueFactory(item -> {
                    try {
                        Object value = method.invoke(item.getValue());
                        return new SimpleStringProperty(value != null ? value.toString() : "");
                    } catch (Exception exception) {
                        LogManager.getLogger(this.getClass()).error(exception::getMessage);
                        return new SimpleStringProperty("Error");
                    }
                });
                this.getColumns().add(column);
            }
        }
    }

    private boolean isGetter(Method method) {
        boolean isGetter = method.getName().startsWith(GET) && !method.getReturnType().equals(void.class);
        boolean isBooleanGetter = method.getName().startsWith(IS) &&
                (method.getReturnType().equals(boolean.class) || method.getReturnType().equals(Boolean.class));
        return method.getParameterCount() == 0 && (isGetter || isBooleanGetter);
    }

    private String extractFieldName(Method getter) {
        if (getter.getName().startsWith(IS)) {
            return getter.getName().substring(IS.length());
        } else {
            return getter.getName().substring(GET.length());
        }
    }
}
