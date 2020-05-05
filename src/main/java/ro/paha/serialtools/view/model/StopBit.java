package ro.paha.serialtools.view.model;

import ro.paha.serialtools.ComPort;

import javax.swing.*;

public class StopBit extends AbstractListModel implements ComboBoxModel {

    ComPort.STOPBITS[] data = new ComPort.STOPBITS[]{
            ComPort.STOPBITS.SB_1,
            ComPort.STOPBITS.SB_1_5,
            ComPort.STOPBITS.SB_2
    };

    ComPort.STOPBITS selection = null;

    public Object getElementAt(int index) {
        return data[index];
    }

    public int getSize() {
        return data.length;
    }

    public void setSelectedItem(Object anItem) {
        selection = (ComPort.STOPBITS) anItem;
    }

    public Object getSelectedItem() {
        return selection;
    }
}