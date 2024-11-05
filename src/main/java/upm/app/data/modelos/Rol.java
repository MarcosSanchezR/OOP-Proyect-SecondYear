package upm.app.data.modelos;

import java.util.Arrays;
import java.util.List;

public enum Rol {
    ADMIN, PLAYER, REFEREE, NONE;

    public static List<Rol> all() {
        return Arrays.asList(Rol.values());
    }

    public static List<Rol> autorized() {
        return all().stream()
                .filter(rol -> rol != Rol.NONE)
                .toList();
    }

}

