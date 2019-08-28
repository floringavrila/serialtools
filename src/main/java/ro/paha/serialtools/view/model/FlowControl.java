package ro.paha.serialtools.view.model;

import ro.paha.serialtools.Port;

import javax.swing.*;

public class FlowControl extends AbstractListModel implements ComboBoxModel {

    Port.FLOWCONTROL[] data = new Port.FLOWCONTROL[]{
            Port.FLOWCONTROL.NONE,
            Port.FLOWCONTROL.RTS_CTS,
            Port.FLOWCONTROL.XON_XOFF
    };

    Port.FLOWCONTROL selection = null;

    public Object getElementAt(int index) {
        return data[index];
    }

    public int getSize() {
        return data.length;
    }

    public void setSelectedItem(Object anItem) {
        selection = (Port.FLOWCONTROL) anItem;
    }

    public Object getSelectedItem() {
        return selection;
    }
}