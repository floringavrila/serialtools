package ro.paha.serialtools.console;

import com.beust.jcommander.JCommander;

public class Console {
    public static void main(String[] args) {
        CommandLineArgs arguments = new CommandLineArgs();
        JCommander.newBuilder()
                .addObject(arguments)
                .build()
                .parse(args);

        System.out.println(arguments.getVerbose().intValue());
    }
}
