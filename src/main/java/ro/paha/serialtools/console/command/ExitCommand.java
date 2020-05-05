package ro.paha.serialtools.console.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.ExitRequest;
import org.springframework.shell.standard.commands.Quit;
import ro.paha.serialtools.Connector;
import ro.paha.serialtools.console.shell.ShellHelper;

@ShellComponent
public class ExitCommand implements Quit.Command {

    @Autowired
    Connector connector;

    @Autowired
    ShellHelper shellHelper;

    @ShellMethod(value = "Exit the shell.", key = {"quit", "exit"})
    public void quit() {
        try {
            connector.closeAll();
            shellHelper.printInfo("Closed all ports. BYE!");
        } catch (Exception e) {
            shellHelper.printError("Closing error: " + e.getMessage());
        }

        throw new ExitRequest();
    }
}

