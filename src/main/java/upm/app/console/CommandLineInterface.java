package upm.app.console;

import upm.app.data.modelos.User;
import upm.app.services.UserService;

import java.time.LocalDate;
import java.util.List;
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
    private final View view;

    public CommandLineInterface(UserService userService, View view) {
        this.userService = userService;
        this.view=view;
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
        this.view.show("Bienvenido a la aplicacion");
        String command=scanner.next();
        boolean exit=false;
        switch (command){
            case CREATE_USER:
                this.createUser(scanner.next().split(DELIMITER));
                break;
            case DELETE_USER:
                this.deleteByDni(scanner.next().split(DELIMITER));
                break;
            case FIND_ALL:
                this.listAll(scanner.next().split(DELIMITER));
                break;
            case HELP:
                this.help();
                break;
            case EXIT:
                exit=true;
                break;
        }
    return exit;
    }

    private void createUser(String[] values){
    if (values.length!=3){
        throw new IllegalArgumentException("El numero de parametros no es el adecuado, se necesitan 3");
    }
        User createdUser=this.userService.create(new User(values[0], LocalDate.parse(values[1]), values[2]));
        this.view.show(createdUser.toString());
    }

    private void deleteByDni(String[] values){
        if (values.length!=1){
            throw new IllegalArgumentException("El numero de parametros no es el adecuado, se necesita 1");
        }
        this.userService.deleteByDni(values[0]);
        this.view.show("Usuario borrado");
    }

    private void listAll(String[] values){
        if (values.length!=0){
            throw new IllegalArgumentException("El numero de parametros no es el adecuado, no es necesario ningun paarametro");
        }
        List<User> list=this.userService.listAll();
        this.view.show(list.toString());
    }

    private void help(){
        this.view.show(HELP);
        this.view.show(CREATE_USER);
        this.view.show(DELETE_USER);
        this.view.show(FIND_ALL);
        this.view.show(EXIT);
    }


}
