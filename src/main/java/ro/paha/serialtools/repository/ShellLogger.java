package ro.paha.serialtools.repository;

import ro.paha.serialtools.console.config.SpringContext;
import ro.paha.serialtools.console.shell.ShellHelper;

public class ShellLogger implements Repository {

    @Override
    public boolean open() {
        return true;
    }

    public void write(String message) {
        getShellHelper().printInfo(message);
    }

    @Override
    public boolean close() {
        return true;
    }

    private ShellHelper getShellHelper() {
        return SpringContext.getBean(ShellHelper.class);
    }
}
