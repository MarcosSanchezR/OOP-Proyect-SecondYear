package upm.app.gui;

import upm.app.data.modelos.Rol;
import upm.app.data.modelos.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

public class Controller {
    public static final String LOGIN = "login";
    public static final String LOGOUT = "logout";
    private final Map<String, Command> commands;
    private User user;
    private Consumer<String> userListener;

    public Controller() {
        this.commands = new HashMap<>();
    }

    public void add(Command command) {
        this.commands.put(command.name(), command);
    }

    public void setUser(User user) {
        this.user = user;
        if (userListener != null) {
            this.userListener.accept(this.userName());
        }

    }

    public String userName() {
        if (Objects.isNull(this.user)) {
            return "Not logged";
        } else {
            return this.user.getName();
        }
    }

    public List<String> keys() {
        return this.commands.keySet().stream()
                .filter(aKey -> this.command(aKey).allowedRoles().contains(this.userRol()))
                .filter(aKey -> !aKey.equals(LOGIN))
                .filter(aKey -> !aKey.equals(LOGOUT))
                .toList();
    }

    public Command command(String key) {
        return this.commands.get(key);
    }

    public Rol userRol() {
        if (Objects.isNull(this.user)) {
            return Rol.NONE;
        } else {
            return this.user.getRol();
        }
    }

    public void setListenerToUser(Consumer<String> listener) {
        this.userListener = listener;
    }
}
