package ro.paha.serialtools;


import com.fazecast.jSerialComm.SerialPort;
import ro.paha.serialtools.delimiter.Delimiter;
import ro.paha.serialtools.repository.Repository;
import ro.paha.serialtools.view.PortOpenException;

import java.util.ArrayList;


public class Connector {

    private ArrayList<ComPort> connections = new ArrayList<>();

    public ComPort[] getCommPorts() {
        SerialPort[] SystemPorts = SerialPort.getCommPorts();
        ComPort[] ports = new ComPort[SystemPorts.length];
        for (int i = 0; i < SystemPorts.length; i++) {
            ComPort alreadyConnected = this.getConnectedPortModel(SystemPorts[i].getSystemPortName());
            if (alreadyConnected != null) {
                ports[i] = alreadyConnected;
            } else {
                ports[i] = new ComPort(
                        SystemPorts[i].getSystemPortName(),
                        SystemPorts[i].getDescriptivePortName(),
                        SystemPorts[i].getPortDescription()
                );
            }
        }

        return ports;
    }

    public void connectToPort(ComPort port, Repository repository, Delimiter delimiterSequence) throws PortOpenException {
        SerialPort comPort = SerialPort.getCommPort(port.getId());
        comPort.setComPortParameters(
                port.getBaudRate(),
                port.getDataBits(),
                port.getStopBits(),
                port.getParity()
        );
        comPort.setFlowControl(SerialPort.FLOW_CONTROL_XONXOFF_IN_ENABLED | SerialPort.FLOW_CONTROL_XONXOFF_OUT_ENABLED);
        if (comPort.openPort()) {
            // each port will have it's own listener
            DataReceivedListener listener = new DataReceivedListener(
                    delimiterSequence,
                    repository
            );
            comPort.addDataListener(listener);
            port.setComPort(comPort);
            connections.add(port);
        } else {
            throw new PortOpenException("Error opening " + port.getName() + ".(Port busy)");
        }

    }

    public ComPort getConnectedPortModel(String portId) {
        for (ComPort port : connections) {
            if (port.getId().equals(portId)) {
                return port;
            }
        }

        return null;
    }

    public void closePort(ComPort port) {
        cleanUpPort(port);
        connections.remove(port);
    }

    public void closeAll() {
        connections.forEach(this::cleanUpPort);
        connections.clear();
    }

    private void cleanUpPort(ComPort port) {
        SerialPort comPort = port.getComPort();
        comPort.removeDataListener();
        comPort.closePort();
        port.removeComPort();
    }
}