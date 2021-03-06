package ro.paha.serialtools.view.action;

import ro.paha.serialtools.ComPort;
import ro.paha.serialtools.Connector;
import ro.paha.serialtools.DataReceivedListener;
import ro.paha.serialtools.delimiter.Delimiter;
import ro.paha.serialtools.repository.Repository;
import ro.paha.serialtools.view.FormException;
import ro.paha.serialtools.view.PortOpenException;
import ro.paha.serialtools.view.PortSettingsPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class ConnectToPort extends AbstractAction {
    private Connector conector;
    private Delimiter delimiter;
    private Repository repository;
    private PortSettingsPanel settingsPanel;

    private static String stringConnect = "Connect";
    private static String stringDisconnect = "Disconnect";

    public ConnectToPort(Connector delegate, Repository repository, Delimiter delimiter, PortSettingsPanel panel) {
        super(stringConnect);
        this.conector = delegate;
        this.settingsPanel = panel;
        this.delimiter = delimiter;
        this.repository = repository;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JToggleButton toggleButton = (JToggleButton) e.getSource();
        boolean isComplete = false;
        try {
            if (toggleButton.isSelected()) {
                connectToPort();
                toggleButton.setText(stringDisconnect);
            } else {
                disconnectFromPort();
                toggleButton.setText(stringConnect);
            }
            settingsPanel.toggleDropDowns(!toggleButton.isSelected());
            isComplete = true;
        } catch (FormException exception) {
            JOptionPane.showMessageDialog(settingsPanel.getPanel(), "" + exception.getMessage(),
                    "Connection error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception exception) {
            exception.printStackTrace();
            JOptionPane.showMessageDialog(settingsPanel.getPanel(), "" + exception.getMessage(),
                    "Connection error", JOptionPane.ERROR_MESSAGE);
        } finally {
            if (!isComplete) {
                toggleButton.setSelected(!toggleButton.isSelected());
            }
        }
    }

    private void connectToPort() throws PortOpenException {
        ComPort port = settingsPanel.getSelectedPort();
        conector.connectToPort(port);
        port.getSerialPort().addDataListener(new DataReceivedListener(
                delimiter,
                repository
        ));
    }

    private void disconnectFromPort() {
        conector.closePort(settingsPanel.getSelectedPort());
    }
}
