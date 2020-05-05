package ro.paha.serialtools;

import ro.paha.serialtools.console.ConsoleApplication;
import ro.paha.serialtools.view.ArduinoSerial;
import java.lang.reflect.InvocationTargetException;

public class Dispatcher {

    private static Class<ArduinoSerial> guiApp = ArduinoSerial.class;
    private static Class<ConsoleApplication> consoleApp = ConsoleApplication.class;

    public static void main(String[] args) {
        try {
            if (args.length > 0) {
                consoleApp.getMethod("main", String[].class)
                        .invoke(null, (Object) args);
            } else {
                guiApp.getMethod("main", String[].class)
                        .invoke(null, (Object) args);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}
