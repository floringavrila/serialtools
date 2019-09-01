package ro.paha.serialtools.view;

import java.awt.*;
import javax.swing.*;

import ro.paha.serialtools.Connector;
import ro.paha.serialtools.Port;
import ro.paha.serialtools.delimiter.Delimiter;
import ro.paha.serialtools.repository.Repository;
import ro.paha.serialtools.view.action.ConnectToPort;
import ro.paha.serialtools.view.action.RefreshPortList;
import ro.paha.serialtools.view.model.*;

public final class PortSettingsPanel {

    private Connector connector;
    private JPanel parentPanel;
    private JComboBox<Port> portSelection;
    private JComboBox<BaudRate> baudRateSelection;
    private JComboBox<DataBit> dataBitsSelection;
    private JComboBox<StopBit> stopBitsSelection;
    private JComboBox<Parity> paritySelection;
    private JComboBox<FlowControl> flowControlSelection;
    private JButton refreshPortsButton;
    private JTextField deviceName;

    public PortSettingsPanel(final Connector connector, Repository repository, Delimiter delimiter) {

        this.connector = connector;

        parentPanel = new JPanel();
        parentPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        parentPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1), ""));
        // Add another subPanel for better alignment
        addInnerPanel(parentPanel);

        initDeviceNameField();
        initRefreshButton();
        initPortList();
        initBaudRate();
        initDataBitsList();
        initStopBitsList();
        initParityList();
//        initFlowControlList();
        initConnectButton(connector, repository, delimiter);
    }

    public JPanel getPanel() {
        return parentPanel;
    }

    private void validate() {
        Port selected = (Port) portSelection.getSelectedItem();
        if (selected == null) {
            throw new FormException("Please select a port!");
        }
    }

    public Port getSelectedPort() {
        validate();
        Port selected = (Port) portSelection.getSelectedItem();

        String portName = deviceName.getText();
        if (portName == null) {
            portName = selected.getName();
            deviceName.setText(portName);
        }
        selected.setNickName(portName);

        selected.setBaudRate(((Port.BAUDRATE) baudRateSelection.getSelectedItem()).getValue());
        selected.setDataBits(((Port.DATABITS) dataBitsSelection.getSelectedItem()).getValue());
        selected.setStopBits(((Port.STOPBITS) stopBitsSelection.getSelectedItem()).getValue());
        selected.setParity(((Port.PARITY) paritySelection.getSelectedItem()).getValue());

        return selected;
    }

    public void setPortsList(Port[] ports) {
        portSelection.removeAllItems();
        for (int p = 0; p < ports.length; p++) {
            portSelection.addItem(ports[p]);
        }
    }

    public void initDeviceNameField() {
        deviceName = new JTextField();
        deviceName.setPreferredSize(new Dimension(160, 20));
        getInnerPanel().add(deviceName);
    }

    public void initRefreshButton() {
        // Connect/Disconnect button
        refreshPortsButton = new JButton("Refresh");
        refreshPortsButton.setAction(new RefreshPortList(connector, this));
        refreshPortsButton.setEnabled(true);
        getInnerPanel().add(new JLabel(""));
        getInnerPanel().add(refreshPortsButton);
    }

    public void initPortList() {
        getInnerPanel().add(new JLabel("Com port :"));
        portSelection = new JComboBox<>();
        setPortsList(connector.getCommPorts());
        getInnerPanel().add(portSelection);
    }

    public void initFlowControlList() {
        getInnerPanel().add(new JLabel("Stop bits :"));
        this.flowControlSelection = new JComboBox<FlowControl>(new FlowControl());
        getInnerPanel().add(flowControlSelection);
    }

    public void initParityList() {
        getInnerPanel().add(new JLabel("Parity :"));
        paritySelection = new JComboBox<Parity>(new Parity());
        paritySelection.setSelectedItem(Port.PARITY.P_NONE);
        getInnerPanel().add(paritySelection);
    }

    public void initStopBitsList() {
        getInnerPanel().add(new JLabel("Stop bits :"));
        stopBitsSelection = new JComboBox<StopBit>(new StopBit());
        stopBitsSelection.setSelectedItem(Port.STOPBITS.SB_1);
        getInnerPanel().add(stopBitsSelection);
    }

    public void initDataBitsList() {
        getInnerPanel().add(new JLabel("Data bits :"));
        dataBitsSelection = new JComboBox<DataBit>(new DataBit());
        dataBitsSelection.setSelectedItem(Port.DATABITS.DB_8);
        getInnerPanel().add(dataBitsSelection);
    }

    public void initBaudRate() {
        getInnerPanel().add(new JLabel("Baud rate :"));
        baudRateSelection = new JComboBox<BaudRate>(new BaudRate());
        baudRateSelection.setSelectedItem(Port.BAUDRATE.B9600);
        getInnerPanel().add(baudRateSelection);
    }

    public void initConnectButton(Connector connector, Repository repository, Delimiter delimiter) {
        final JToggleButton toggleButton = new JToggleButton("Connect");
        toggleButton.setAction(
                new ConnectToPort(
                        connector,
                        repository,
                        delimiter,
                        this
                ));
        toggleButton.setEnabled(true);
        getInnerPanel().add(new JLabel(""));
        getInnerPanel().add(toggleButton);
    }

    public void toggleDropDowns(boolean status) {
        deviceName.setEditable(status);
        refreshPortsButton.setEnabled(status);
        portSelection.setEnabled(status);
        baudRateSelection.setEnabled(status);
        dataBitsSelection.setEnabled(status);
        stopBitsSelection.setEnabled(status);
        paritySelection.setEnabled(status);
    }

    private JPanel getInnerPanel() {
        Component[] components = parentPanel.getComponents();
        return (JPanel) components[0];
    }

    private void addInnerPanel(JPanel parent) {
        JPanel innerJPanel = new JPanel();
        innerJPanel.setLayout(new GridLayout(0, 2, 0, 14));
        innerJPanel.setBorder(BorderFactory.createEmptyBorder(8, 5, 5, 0));
        innerJPanel.add(new JLabel("Name :"));
        parent.add(innerJPanel);
    }
}
