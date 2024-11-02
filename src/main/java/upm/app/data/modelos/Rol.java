package upm.app.data.modelos;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public enum Rol {
    ADMIN, PLAYER, NONE;

    public static List<Rol> all(){
        return new ArrayList<>(EnumSet.allOf(Rol.class));
    }

    public static List<Rol> autorized(){
    return new ArrayList<>(EnumSet.complementOf(EnumSet.of(NONE)));
    }
}

