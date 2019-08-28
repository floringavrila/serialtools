package ro.paha.serialtools.view.model;

import ro.paha.serialtools.Port;

import javax.swing.*;

public class BaudRate extends AbstractListModel implements ComboBoxModel {

    Port.BAUDRATE[] data = new Port.BAUDRATE[]{
            Port.BAUDRATE.B4800,
            Port.BAUDRATE.B9600,
            Port.BAUDRATE.B14400,
            Port.BAUDRATE.B19200,
            Port.BAUDRATE.B28800,
            Port.BAUDRATE.B38400,
            Port.BAUDRATE.B57600,
            Port.BAUDRATE.B115200
    };

    Port.BAUDRATE selection = null;

    public Object getElementAt(int index) {
        return data[index];
    }

    public int getSize() {
        return data.length;
    }

    public void setSelectedItem(Object anItem) {
        selection = (Port.BAUDRATE) anItem;
    }

    public Object getSelectedItem() {
        return selection;
    }
}