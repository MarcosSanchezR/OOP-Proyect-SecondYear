package upm.app;

import upm.app.console.CommandLineInterface;
import upm.app.console.ErrorHandler;
import upm.app.console.View;
import upm.app.data.repositorios.UserRepository;
import upm.app.data.repositorios.map.UserRepositoryMap;
import upm.app.services.UserService;


public class DependencyInjector {
    private final ErrorHandler errorHandler;
    private final View view;
    private final CommandLineInterface commandLineInterface;
    private final UserRepository userRepository;
    private final UserService userService;

    public DependencyInjector(){
        this.userRepository=new UserRepositoryMap();

        this.userService=new UserService(this.userRepository);

        this.view= new View();
        this.commandLineInterface=new CommandLineInterface(userService, view);

        this.errorHandler=new ErrorHandler(this.commandLineInterface, this.view);
    }

    public void run(){
        this.errorHandler.handleErrors();
    }

    public ErrorHandler getErrorHandler() {
        return errorHandler;
    }

    public View getView() {
        return view;
    }

    public CommandLineInterface getCommandLineInterface() {
        return commandLineInterface;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public UserService getUserService() {
        return userService;
    }
}
