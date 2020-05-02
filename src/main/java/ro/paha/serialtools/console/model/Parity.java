package ro.paha.serialtools.console.model;

import ro.paha.serialtools.ComPort;

import java.util.HashMap;
import java.util.Map;

public class Parity {
    ComPort.PARITY[] items = new ComPort.PARITY[]{
            ComPort.PARITY.P_NONE,
            ComPort.PARITY.P_EVEN,
            ComPort.PARITY.P_ODD,
            ComPort.PARITY.P_MARK,
            ComPort.PARITY.P_SPACE
    };

    public Map<String, String> getOptions() {
        Map<String, String> options = new HashMap<>();
        options.put("1", ComPort.PARITY.P_NONE.toString());
        options.put("2", ComPort.PARITY.P_EVEN.toString());
        options.put("3", ComPort.PARITY.P_ODD.toString());
        options.put("4", ComPort.PARITY.P_MARK.toString());
        options.put("5", ComPort.PARITY.P_SPACE.toString());

        return options;
    }

    public String getDefaultValue() {
        return "1";
    }

    public int getOptionValueByIndex(String index) {
        for (ComPort.PARITY item : items) {
            if (item.toString().equals(this.getOptions().get(index))) {
                return item.getValue();
            }
        }

        return 0;
    }
}
