package upm.app;

import upm.app.console.CommandLineInterface;
import upm.app.console.View;
import upm.app.data.repositorios.UserRepositoryMap;
import upm.app.services.UserService;

public class App {


    public static void main(String[] args) {
        System.out.println("Run. App-> Version0");
        View view = new View();
        UserRepositoryMap userRepositoryMap = new UserRepositoryMap();
        UserService userService = new UserService(userRepositoryMap);
        CommandLineInterface commandLineInterface = new CommandLineInterface(userService, view);

        boolean exit = false;
        while (!exit) {
            try {
                exit = commandLineInterface.runCommands();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println("Fin de la ejecucion");
    }
}
