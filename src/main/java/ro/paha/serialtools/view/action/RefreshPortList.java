package ro.paha.serialtools.view.action;

import ro.paha.serialtools.Connector;
import ro.paha.serialtools.view.PortSettingsPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class RefreshPortList extends AbstractAction {
    private PortSettingsPanel panel;
    private Connector conector;

    public RefreshPortList(Connector conector, PortSettingsPanel panel) {
        super("Refresh");
        this.panel = panel;
        this.conector = conector;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        panel.setPortsList(conector.getCommPorts());
    }
}
