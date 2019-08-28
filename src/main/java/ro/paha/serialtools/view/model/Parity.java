package ro.paha.serialtools.view.model;

import ro.paha.serialtools.Port;

import javax.swing.*;

public class Parity extends AbstractListModel implements ComboBoxModel {

    Port.PARITY[] data = new Port.PARITY[]{
            Port.PARITY.P_NONE,
            Port.PARITY.P_EVEN,
            Port.PARITY.P_ODD,
            Port.PARITY.P_MARK,
            Port.PARITY.P_SPACE
    };

    Port.PARITY selection = null;

    public Object getElementAt(int index) {
        return data[index];
    }

    public int getSize() {
        return data.length;
    }

    public void setSelectedItem(Object anItem) {
        selection = (Port.PARITY) anItem;
    }

    public Object getSelectedItem() {
        return selection;
    }
}