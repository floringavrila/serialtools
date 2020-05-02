package ro.paha.serialtools.console.model;

import ro.paha.serialtools.ComPort;

import java.util.HashMap;
import java.util.Map;

public class DataBits {

    ComPort.DATABITS[] items = new ComPort.DATABITS[]{
            ComPort.DATABITS.DB_6,
            ComPort.DATABITS.DB_7,
            ComPort.DATABITS.DB_8
    };

    public Map<String, String> getOptions() {
        Map<String, String> options = new HashMap<>();
        options.put("1", ComPort.DATABITS.DB_6.toString());
        options.put("2", ComPort.DATABITS.DB_7.toString());
        options.put("3", ComPort.DATABITS.DB_8.toString());

        return options;
    }

    public String getDefaultValue() {
        return "3";
    }

    public int getOptionValueByIndex(String index) {
        for (ComPort.DATABITS item : items) {
            if (item.toString().equals(this.getOptions().get(index))) {
                return item.getValue();
            }
        }

        return 0;
    }
}
