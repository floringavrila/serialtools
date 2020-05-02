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
}
