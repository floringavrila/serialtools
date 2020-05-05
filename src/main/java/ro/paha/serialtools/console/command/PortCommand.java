package ro.paha.serialtools.console.command;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.shell.table.Formatter;
import org.springframework.shell.table.*;
import org.springframework.util.StringUtils;
import ro.paha.serialtools.ComPort;
import ro.paha.serialtools.Connector;
import ro.paha.serialtools.DataReceivedListener;
import ro.paha.serialtools.console.model.BaudRate;
import ro.paha.serialtools.console.model.DataBits;
import ro.paha.serialtools.console.model.Parity;
import ro.paha.serialtools.console.model.StopBits;
import ro.paha.serialtools.console.shell.InputReader;
import ro.paha.serialtools.console.shell.ShellHelper;
import ro.paha.serialtools.delimiter.LineFeed;
import ro.paha.serialtools.repository.ShellLogger;
import ro.paha.serialtools.view.PortOpenException;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

@ShellComponent
public class PortCommand {

    @Autowired
    ShellHelper shellHelper;

    @Autowired
    Connector connector;

    @Autowired
    InputReader inputReader;

    private AtomicBoolean listenEnabled = new AtomicBoolean(true);

    @ShellMethod("Display available Serial Ports and status")
    public void list() {
        List<ComPort> ports = Arrays.asList(connector.getCommPorts());

        LinkedHashMap<String, Object> headers = new LinkedHashMap<>();
        headers.put("id", "Id");
        headers.put("name", "Name");
        headers.put("nickName", "Nickname");
        headers.put("BaudRate", "Baud Rate");
        headers.put("DataBits", "Data Bits");
        headers.put("StopBits", "Stop Bits");
        headers.put("Parity", "Parity");
        headers.put("isConnected", "Status");

        TableModel model = new BeanListTableModel<>(ports, headers);
        TableBuilder tableBuilder = new TableBuilder(model);
        tableBuilder.on(CellMatchers.column(7)).addFormatter(new Formatter() {
            @Override
            public String[] format(Object value) {
                // skip the header
                if (value instanceof String) {
                    return new String[]{
                            (String) value
                    };
                }
                return new String[]{
                        (boolean) value ? "Is Connected" : "Not connected"
                };
            }
        });


        tableBuilder.addFullBorder(BorderStyle.oldschool);
        shellHelper.print(tableBuilder.build().render(80));
    }

    @ShellMethod("Connect to a Serial Port")
    public void connect(@ShellOption({"--id"}) String portId) {
        ComPort port = null;
        for (ComPort comPort : connector.getCommPorts()) {
            if (comPort.getId().equals(portId)) {
                port = comPort;
            }
        }
        if (port == null) {
            shellHelper.printError(
                    String.format("Invalid port ID='%s' --> ABORTING", portId)
            );

            return;
        }

        if (port.getIsConnected()) {
            shellHelper.printError(
                    String.format("Port already connected ID='%s' --> ABORTING", portId)
            );

            return;
        }

        // 1. read nickname --------------------------------------------
        do {
            String nickName = inputReader.prompt("Nickname");
            if (StringUtils.hasText(nickName)) {
                port.setNickName(nickName);
            } else {
                shellHelper.printWarning("Port's Nickname CAN NOT be empty string? Please enter valid value!");
            }
        } while (port.getNickName().equals(""));

        // 2. read BAUDRATE ----------------------------------------------
        BaudRate baudRateModel = new BaudRate();
        String selectedBaudRate = inputReader.selectFromList(
                "BaudRate",
                "Please enter one of the [] values",
                baudRateModel.getOptions(),
                true,
                baudRateModel.getDefaultValue()
        );
        port.setBaudRate(
                baudRateModel.getOptionValueByIndex(selectedBaudRate)
        );

        // 3. read DATABITS ----------------------------------------------
        DataBits dataBitsModel = new DataBits();
        String selecteddataBits = inputReader.selectFromList(
                "DataBits",
                "Please enter one of the [] values",
                dataBitsModel.getOptions(),
                true,
                dataBitsModel.getDefaultValue()
        );
        port.setDataBits(
                dataBitsModel.getOptionValueByIndex(selecteddataBits)
        );

        // 4. read STOPBITS ----------------------------------------------
        StopBits stopBitsModel = new StopBits();

        String selectedStopBits = inputReader.selectFromList(
                "StopBits",
                "Please enter one of the [] values",
                stopBitsModel.getOptions(),
                true,
                stopBitsModel.getDefaultValue()
        );
        port.setStopBits(
                stopBitsModel.getOptionValueByIndex(selectedStopBits)
        );

        // 5. read PARITY ----------------------------------------------
        Parity parityModel = new Parity();
        String selectedParity = inputReader.selectFromList(
                "Parity",
                "Please enter one of the [] values",
                parityModel.getOptions(),
                true,
                parityModel.getDefaultValue()
        );
        port.setParity(
                parityModel.getOptionValueByIndex(selectedParity)
        );

        try {
            connector.connectToPort(port);
            shellHelper.printSuccess("Connected to port id=" + port.getId());
        } catch (PortOpenException e) {
            shellHelper.printError(
                    String.format("Connection error: '%s' --> ABORTING", e.getMessage())
            );
        } catch (Exception e) {
            shellHelper.printError(
                    String.format("Connection error: '%s' --> ABORTING", e.getMessage())
            );
        }
    }

    @ShellMethod("Show received data of a Serial Port")
    public void listen(@ShellOption({"--id"}) String portId) {
        this.listenEnabled.set(true);
        ComPort port = connector.getConnectedPortModel(portId);
        if (port == null) {
            shellHelper.printError(
                    String.format("Port is not yet configured ID='%s' --> ABORTING", portId)
            );
            return;
        }

        port.getSerialPort().addDataListener(new DataReceivedListener(
                new LineFeed(),
                new ShellLogger()
        ));
        shellHelper.printError("Started to listen! press any key + ENTER to abort!");
        do {
            String someText = inputReader.prompt("");
            if (StringUtils.hasText(someText)) {
                port.getSerialPort().removeDataListener();
                this.listenEnabled.set(false);
            }
        } while (this.listenEnabled.get());
    }

    @ShellMethod("Disconnect from a Serial Port")
    public void disconnect(@ShellOption({"--id"}) String portId) {
        ComPort port = null;
        for (ComPort comPort : connector.getCommPorts()) {
            if (comPort.getId().equals(portId)) {
                port = comPort;
            }
        }
        if (port == null) {
            shellHelper.printError(
                    String.format("Invalid port ID='%s' --> ABORTING", portId)
            );

            return;
        }

        if (!port.getIsConnected()) {
            shellHelper.printError(
                    String.format("Port already disconnected ID='%s' --> ABORTING", portId)
            );

            return;
        }

        connector.closePort(port);

        shellHelper.printSuccess(
                String.format("Disconnected from Port ID='%s'", port.getId())
        );
    }
}

