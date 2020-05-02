package ro.paha.serialtools.console.model;

import ro.paha.serialtools.ComPort;

import java.util.HashMap;
import java.util.Map;

public class StopBits {

    ComPort.STOPBITS[] items = new ComPort.STOPBITS[]{
            ComPort.STOPBITS.SB_1,
            ComPort.STOPBITS.SB_1_5,
            ComPort.STOPBITS.SB_2
    };

    public Map<String, String> getOptions() {
        Map<String, String> options = new HashMap<>();
        options.put("1", ComPort.STOPBITS.SB_1.toString());
        options.put("2", ComPort.STOPBITS.SB_1_5.toString());
        options.put("3", ComPort.STOPBITS.SB_2.toString());

        return options;
    }

    public String getDefaultValue() {
        return "1";
    }

    public int getOptionValueByIndex(String index) {
        for (ComPort.STOPBITS item : items) {
            if (item.toString().equals(this.getOptions().get(index))) {
                return item.getValue();
            }
        }

        return 0;
    }
}
