package upm.app.console;

public enum CommandNames {
    CREATE_USER("create-user", ". Se crea un usuario.", "<nombre>","<aaaa-mm-dd>","<dni>"),
    DELETE_USER("delete-user", ". Borra el usuario a traves del dni." , ":<dni>"),
    FIND_ALL("find-all", ". Muestra  todos los usuarios."),
    HELP("help", ". Muestra la ayuda de los comandos."),
    EXIT("exit", ". Termina la ejecucion.");

    private final String value;
    private final String help;
    private final String[] params;

    CommandNames(String value, String help, String... params) {
        this.value = value;
        this.help = help;
        this.params=params;
    }

    public static CommandNames fromValue(String value) {
        for (CommandNames command : CommandNames.values()) {
            if (command.getValue().equals(value)) {
                return command;
            }
        }
        throw new IllegalArgumentException("El comando -" + value + "- no existe");
    }

    public String getHelp() {
        String finalHelp=this.getValue();
        if(this.params.length!=0){
            finalHelp+=Delimiters.COMMAND.getValue()+String.join(Delimiters.PARAM.getValue(), this.params);
        }
        return finalHelp+this.help;
    }

    public String getValue() {
        return this.value;
    }

    public String[] getParams() {
        return params;
    }
}
