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
import ro.paha.serialtools.console.shell.InputReader;
import ro.paha.serialtools.console.shell.ShellHelper;
import ro.paha.serialtools.delimiter.LineFeed;
import ro.paha.serialtools.repository.Console;
import ro.paha.serialtools.view.PortOpenException;

import java.util.*;

@ShellComponent
public class PortCommand {

    @Autowired
    ShellHelper shellHelper;

    @Autowired
    Connector connector;

    @Autowired
    InputReader inputReader;

    @ShellMethod("Display available ports and status")
    public void portList() {
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
        tableBuilder.addInnerBorder(BorderStyle.fancy_light);
        tableBuilder.addHeaderBorder(BorderStyle.fancy_double);
        shellHelper.print(tableBuilder.build().render(80));
    }

    @ShellMethod("Connect to a COMM port")
    public void portConnect(@ShellOption({"--id"}) String portId) {

        ComPort port = null;
        for (ComPort comPort : connector.getCommPorts()) {
            if (comPort.getId().equals(portId)) {
                port = new ComPort(
                        comPort.getId(),
                        comPort.getName(),
                        comPort.getDescription()
                );
            }
        }
        if (port == null) {
            shellHelper.printError(
                    String.format("Invalid port ID='%s' --> ABORTING", portId)
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

        // 3. read user's Gender ----------------------------------------------
        ComPort.BAUDRATE[] baudRates = new ComPort.BAUDRATE[]{
                ComPort.BAUDRATE.B4800,
                ComPort.BAUDRATE.B9600,
                ComPort.BAUDRATE.B14400,
                ComPort.BAUDRATE.B19200,
                ComPort.BAUDRATE.B28800,
                ComPort.BAUDRATE.B38400,
                ComPort.BAUDRATE.B57600,
                ComPort.BAUDRATE.B115200
        };
        Map<String, String> options = new HashMap<>();
        options.put("1", ComPort.BAUDRATE.B4800.toString());
        options.put("2", ComPort.BAUDRATE.B9600.toString());
        options.put("3", ComPort.BAUDRATE.B14400.toString());
        options.put("4", ComPort.BAUDRATE.B19200.toString());
        options.put("5", ComPort.BAUDRATE.B28800.toString());
        options.put("6", ComPort.BAUDRATE.B38400.toString());
        options.put("7", ComPort.BAUDRATE.B57600.toString());
        options.put("8", ComPort.BAUDRATE.B115200.toString());
        String selectedBaudRate = inputReader.selectFromList("BaudRate", "Please enter one of the [] values", options, true, "2");

        for (ComPort.BAUDRATE baudRate : baudRates) {
            if (baudRate.toString().equals(options.get(selectedBaudRate))) {
                port.setBaudRate(baudRate.getValue());
            }
        }

        ComPort.DATABITS[] dataBits = new ComPort.DATABITS[]{
                ComPort.DATABITS.DB_6,
                ComPort.DATABITS.DB_7,
                ComPort.DATABITS.DB_8
        };
        Map<String, String> dataBitsOptions = new HashMap<>();
        options.put("1", ComPort.DATABITS.DB_6.toString());
        options.put("2", ComPort.DATABITS.DB_7.toString());
        options.put("3", ComPort.DATABITS.DB_8.toString());
        String selecteddataBits = inputReader.selectFromList("DataBits", "Please enter one of the [] values", options, true, "3");

        for (ComPort.DATABITS bit : dataBits) {
            if (bit.toString().equals(dataBitsOptions.get(selecteddataBits))) {
                port.setDataBits(bit.getValue());
            }
        }

        ComPort.STOPBITS[] stopBits = new ComPort.STOPBITS[]{
                ComPort.STOPBITS.SB_1,
                ComPort.STOPBITS.SB_1_5,
                ComPort.STOPBITS.SB_2
        };
        Map<String, String> stopBitsOptions = new HashMap<>();
        options.put("1", ComPort.STOPBITS.SB_1.toString());
        options.put("2", ComPort.STOPBITS.SB_1_5.toString());
        options.put("3", ComPort.STOPBITS.SB_2.toString());
        String selectedStopBits = inputReader.selectFromList("StopBits", "Please enter one of the [] values", options, true, "1");

        for (ComPort.STOPBITS bit : stopBits) {
            if (bit.toString().equals(stopBitsOptions.get(selectedStopBits))) {
                port.setStopBits(bit.getValue());
            }
        }

        ComPort.PARITY[] parity = new ComPort.PARITY[]{
                ComPort.PARITY.P_NONE,
                ComPort.PARITY.P_EVEN,
                ComPort.PARITY.P_ODD,
                ComPort.PARITY.P_MARK,
                ComPort.PARITY.P_SPACE
        };
        Map<String, String> parityOptions = new HashMap<>();
        options.put("1", ComPort.PARITY.P_NONE.toString());
        options.put("2", ComPort.PARITY.P_EVEN.toString());
        options.put("3", ComPort.PARITY.P_ODD.toString());
        options.put("4", ComPort.PARITY.P_MARK.toString());
        options.put("5", ComPort.PARITY.P_SPACE.toString());
        String selectedParity = inputReader.selectFromList("Parity", "Please enter one of the [] values", options, true, "1");

        for (ComPort.PARITY bit : parity) {
            if (bit.toString().equals(parityOptions.get(selectedParity))) {
                port.setParity(bit.getValue());
            }
        }

        Console repo = new Console();
        repo.open();
        try {
            connector.connectToPort(port, repo, new LineFeed());
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
}
