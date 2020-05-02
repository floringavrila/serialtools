package ro.paha.serialtools.console.model;

import ro.paha.serialtools.ComPort;

import java.util.HashMap;
import java.util.Map;

public class BaudRate {

    ComPort.BAUDRATE[] items = new ComPort.BAUDRATE[]{
            ComPort.BAUDRATE.B4800,
            ComPort.BAUDRATE.B9600,
            ComPort.BAUDRATE.B14400,
            ComPort.BAUDRATE.B19200,
            ComPort.BAUDRATE.B28800,
            ComPort.BAUDRATE.B38400,
            ComPort.BAUDRATE.B57600,
            ComPort.BAUDRATE.B115200
    };

    public Map<String, String> getOptions() {
        Map<String, String> options = new HashMap<>();
        options.put("1", ComPort.BAUDRATE.B4800.toString());
        options.put("2", ComPort.BAUDRATE.B9600.toString());
        options.put("3", ComPort.BAUDRATE.B14400.toString());
        options.put("4", ComPort.BAUDRATE.B19200.toString());
        options.put("5", ComPort.BAUDRATE.B28800.toString());
        options.put("6", ComPort.BAUDRATE.B38400.toString());
        options.put("7", ComPort.BAUDRATE.B57600.toString());
        options.put("8", ComPort.BAUDRATE.B115200.toString());

        return options;
    }

    public String getDefaultValue() {
        return "2";
    }

    public int getOptionValueByIndex(String index) {
        for (ComPort.BAUDRATE item : items) {
            if (item.toString().equals(this.getOptions().get(index))) {
                return item.getValue();
            }
        }

        return 0;
    }
}

