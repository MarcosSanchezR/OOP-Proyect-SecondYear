package upm.app.console;

public enum CommandNames {
    CREATE_USER ("create-user", ":<nombre>,<aaaa-mm-dd>,<dni>"),
    DELETE_USER ("delete-user", ":<dni>"),
    FIND_ALL ("find-all", " muestra  todos los usuarios"),
    HELP ("help", " muestra la ayuda de los comandos"),
    EXIT ("exit", " termina la ejecucion");

    private final String value;
    private final String help;

    CommandNames(String value, String help) {
        this.value = value;
        this.help = help;
    }

    public static CommandNames fromValue(String value){
        for(CommandNames command: CommandNames.values()){
            if (command.getValue().equals(value)){
                return command;
            }
        }
    throw new IllegalArgumentException("El comando -"+ value+ "- no existe");
    }

    public String getHelp() {
        return this.getValue()+this.help;
    }

    public String getValue() {
        return this.value;
    }
}
