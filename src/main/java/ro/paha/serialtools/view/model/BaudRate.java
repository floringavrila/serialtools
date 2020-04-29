package ro.paha.serialtools.view.model;

import ro.paha.serialtools.ComPort;

import javax.swing.*;

public class BaudRate extends AbstractListModel implements ComboBoxModel {

    ComPort.BAUDRATE[] data = new ComPort.BAUDRATE[]{
            ComPort.BAUDRATE.B4800,
            ComPort.BAUDRATE.B9600,
            ComPort.BAUDRATE.B14400,
            ComPort.BAUDRATE.B19200,
            ComPort.BAUDRATE.B28800,
            ComPort.BAUDRATE.B38400,
            ComPort.BAUDRATE.B57600,
            ComPort.BAUDRATE.B115200
    };

    ComPort.BAUDRATE selection = null;

    public Object getElementAt(int index) {
        return data[index];
    }

    public int getSize() {
        return data.length;
    }

    public void setSelectedItem(Object anItem) {
        selection = (ComPort.BAUDRATE) anItem;
    }

    public Object getSelectedItem() {
        return selection;
    }
}