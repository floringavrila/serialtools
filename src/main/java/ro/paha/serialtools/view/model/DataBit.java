package ro.paha.serialtools.view.model;

import ro.paha.serialtools.ComPort;

import javax.swing.*;

public class DataBit extends AbstractListModel implements ComboBoxModel {

    ComPort.DATABITS[] data = new ComPort.DATABITS[]{
            ComPort.DATABITS.DB_6,
            ComPort.DATABITS.DB_7,
            ComPort.DATABITS.DB_8
    };

    ComPort.DATABITS selection = null;

    public Object getElementAt(int index) {
        return data[index];
    }

    public int getSize() {
        return data.length;
    }

    public void setSelectedItem(Object anItem) {
        selection = (ComPort.DATABITS) anItem;
    }

    public Object getSelectedItem() {
        return selection;
    }
}