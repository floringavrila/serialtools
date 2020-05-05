package ro.paha.serialtools.view.model;

import ro.paha.serialtools.ComPort;

import javax.swing.*;

public class Parity extends AbstractListModel implements ComboBoxModel {

    ComPort.PARITY[] data = new ComPort.PARITY[]{
            ComPort.PARITY.P_NONE,
            ComPort.PARITY.P_EVEN,
            ComPort.PARITY.P_ODD,
            ComPort.PARITY.P_MARK,
            ComPort.PARITY.P_SPACE
    };

    ComPort.PARITY selection = null;

    public Object getElementAt(int index) {
        return data[index];
    }

    public int getSize() {
        return data.length;
    }

    public void setSelectedItem(Object anItem) {
        selection = (ComPort.PARITY) anItem;
    }

    public Object getSelectedItem() {
        return selection;
    }
}