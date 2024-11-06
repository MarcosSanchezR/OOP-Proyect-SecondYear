package upm.app.console;

import upm.app.console.exceptions.BadRequestException;
import upm.app.console.exceptions.ForbiddenException;
import upm.app.data.modelos.Rol;

import java.util.List;

public enum CommandNames {
    CREATE_USER("create-user", ". Se crea un usuario.", Rol.all(), "<nombre>", "<aaaa-mm-dd>", "<dni>"),
    DELETE_USER("delete-user", ". Borra el usuario a traves del dni.", List.of(Rol.ADMIN), "<dni>"),
    FIND_ALL_USER("find-all-user", ". Muestra  todos los usuarios.", Rol.autorized()),
    CREATE_COURT("create-court", ". Se crea una pista de tenis.", List.of(Rol.ADMIN, Rol.REFEREE), "<nombre>", "<superficie>", "<localizacion>"),
    DELETE_COURT("delete-court", ". Borra la pista a traves del nombre.", List.of(Rol.ADMIN), "<nombre>"),
    FIND_ALL_COURT("find-all-court", ". Muestra todas las pistas.", Rol.autorized()),
    CREATE_MATCH("create-match", ". Se crea un partido", List.of(Rol.REFEREE, Rol.ADMIN), "<aaaa-mm-dd-hh-mm-ss>", "<dni-jugador1>", "<dni-jugador2>", "<nombre-pista>"),
    ESTABLISH_WINNER("establish-winner", ". Establece un ganador al partido", List.of(Rol.REFEREE, Rol.ADMIN), "aaaa-mm-dd-hh-mm-ss (inicio)", "<nombre-pista", "<dni-ganador>"),
    FIND_ALL_MATCH("find-all-match", ". Muestra todos los partidos", Rol.autorized()),
    HELP("help", ". Muestra la ayuda de los comandos.", Rol.all()),
    LOGIN("login", ". Inicia sesion", Rol.all(), "<dni>", "<password>"),
    LOGOUT("logout", ". Cierra sesion", Rol.autorized()),
    EXIT("exit", ". Termina la ejecucion.", Rol.all());


    private final String value;
    private final String help;
    private final String[] params;
    private final List<Rol> allowedRoles;

    CommandNames(String value, String help, List<Rol> roles, String... params) {
        this.value = value;
        this.help = help;
        this.params = params;
        this.allowedRoles = roles;
    }

    public static CommandNames fromValue(String value, Rol userRol) {
        for (CommandNames command : CommandNames.values()) {
            if (command.getValue().equals(value)) {
                if (!command.allowedRoles.contains(userRol)) {
                    throw new ForbiddenException("No estas autorizado");
                }
                return command;
            }
        }
        throw new BadRequestException("El comando -" + value + "- no existe");
    }

    public String getHelp(Rol userRoll) {
        String finalHelp;
        if (this.allowedRoles.contains(userRoll)) {
            finalHelp = this.getValue();
            if (this.params.length != 0) {
                finalHelp += Delimiters.COMMAND.getValue() + String.join(Delimiters.PARAM.getValue(), this.params);
            }
            return finalHelp + this.help;
        } else {
            return "";
        }
    }

    public String getValue() {
        return this.value;
    }

    public String[] getParams() {
        return params;
    }
}
