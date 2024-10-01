package upm.console;

import upm.modelos.User;
import upm.services.UserService;

import java.time.LocalDate;
import java.util.Scanner;

public class CommandLineInterface {
    private static final String CREATE_USER ="create-user";
    private static final String DELETE_USER ="delete-user";
    private static final String FIND_ALL ="find-all";
    private static final String HELP ="help";
    private static final String EXIT ="exit";
    private static final String COMMAND_DELIMITER_PARAMETERS ="[:\\r\\n]";
    private static final String DELIMITER =";";


    private final UserService userService;

    public CommandLineInterface(UserService userService) {
        this.userService = userService;
    }

    public boolean runCommands(){
        Scanner scanner=new Scanner(System.in).useDelimiter(COMMAND_DELIMITER_PARAMETERS);
        boolean exit;
        do{
            exit=runCommands(scanner);
        }while (!exit);
    return true;
    }

    public boolean runCommands(Scanner scanner){
        añadir mensaje
        String command=scanner.next();
        boolean exit=false;
        switch (command){
            case CREATE_USER:
                this.createUser(scanner.next().split(DELIMITER));
                break;
            case DELETE_USER:
                this.deleteByDni(scanner.next().split(DELIMITER));

        }

    }

    private void createUser(String[] values){
    if (values.length!=3){
        throw new IllegalArgumentException("El numero de parametros no es el adecuado, se necesitan 3");
    }
        User createdUser=this.userService.create(new User(values[0], LocalDate.parse(values[1]), values[2]));
        mensage
    }

    private void deleteByDni(String[] values){
        if (values.length!=1){
            throw new IllegalArgumentException("El numero de parametros no es el adecuado, se necesita 1");
        }
        this.userService.deleteByDni(values[0]);
    }

}
