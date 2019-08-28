package ro.paha.serialtools.view.model;

import ro.paha.serialtools.Port;

import javax.swing.*;

public class StopBit extends AbstractListModel implements ComboBoxModel {

    Port.STOPBITS[] data = new Port.STOPBITS[]{
            Port.STOPBITS.SB_1,
            Port.STOPBITS.SB_1_5,
            Port.STOPBITS.SB_2
    };

    Port.STOPBITS selection = null;

    public Object getElementAt(int index) {
        return data[index];
    }

    public int getSize() {
        return data.length;
    }

    public void setSelectedItem(Object anItem) {
        selection = (Port.STOPBITS) anItem;
    }

    public Object getSelectedItem() {
        return selection;
    }
}