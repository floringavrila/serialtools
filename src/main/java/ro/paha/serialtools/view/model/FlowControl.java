package ro.paha.serialtools.view.model;

import ro.paha.serialtools.ComPort;

import javax.swing.*;

public class FlowControl extends AbstractListModel implements ComboBoxModel {

    ComPort.FLOWCONTROL[] data = new ComPort.FLOWCONTROL[]{
            ComPort.FLOWCONTROL.NONE,
            ComPort.FLOWCONTROL.RTS_CTS,
            ComPort.FLOWCONTROL.XON_XOFF
    };

    ComPort.FLOWCONTROL selection = null;

    public Object getElementAt(int index) {
        return data[index];
    }

    public int getSize() {
        return data.length;
    }

    public void setSelectedItem(Object anItem) {
        selection = (ComPort.FLOWCONTROL) anItem;
    }

    public Object getSelectedItem() {
        return selection;
    }
}