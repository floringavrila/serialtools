package ro.paha.serialtools.view;

import java.io.IOException;

public final class PortOpenException extends IOException {

    public PortOpenException(String exceptionMsg) {
        super(exceptionMsg);
    }
}
