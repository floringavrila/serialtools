package ro.paha.serialtools;

import com.fazecast.jSerialComm.*;
import ro.paha.serialtools.delimiter.Delimiter;
import ro.paha.serialtools.repository.Repository;

public class DataReceivedListener implements SerialPortMessageListener {

    private Repository repository;

    private byte[] delimiters;

    public DataReceivedListener(Delimiter delimiter, Repository repository) {
        this.delimiters = delimiter.getDelimiter();
        this.repository = repository;
    }

    @Override
    public byte[] getMessageDelimiter() {
        return delimiters;
    }

    @Override
    public int getListeningEvents() {
        return SerialPort.LISTENING_EVENT_DATA_RECEIVED;
    }

    @Override
    public boolean delimiterIndicatesEndOfMessage() {
        return true;
    }

    @Override
    public void serialEvent(SerialPortEvent event) {
        byte[] delimitedMessage = event.getReceivedData();

        repository.write("[" + event.getSerialPort().getSystemPortName() + "] - " + new String(delimitedMessage));
    }
}
