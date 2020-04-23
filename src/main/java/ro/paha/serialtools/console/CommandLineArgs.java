package ro.paha.serialtools.console;

import com.beust.jcommander.Parameter;

public class CommandLineArgs {
    @Parameter(names = { "-log", "-verbose" }, description = "Level of verbosity")
    private Integer verbose = 1;

    public Integer getVerbose() {
        return verbose;
    }
}
