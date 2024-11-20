package upm.app.console;

import upm.app.data.modelos.Rol;

import java.util.List;

public interface Command {
    String name();

    List<String> params();

    List<Rol> allowedRoles();

    String helpMessage();

    void execute(String[] params);

    default String help(){
        StringBuilder result=new StringBuilder(this.name());
        if(!this.params().isEmpty()){
            result.append(Delimiters.COMMAND.getValue()).append(String.join(Delimiters.PARAM.getValue(), this.params()));
        }
        return result.append(". ").append(this.helpMessage()).append(".").toString();
    }
}
