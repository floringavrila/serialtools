package ro.paha.serialtools.view.action;

import ro.paha.serialtools.view.ArduinoSerial;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class Exit extends AbstractAction {
    private ArduinoSerial app;

    public Exit(ArduinoSerial app) {
        super("Exit");
        this.app = app;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        app.appExit();
    }
}
