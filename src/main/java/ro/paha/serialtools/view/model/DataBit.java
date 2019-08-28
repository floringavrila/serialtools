package ro.paha.serialtools.view.model;

import ro.paha.serialtools.Port;

import javax.swing.*;

public class DataBit extends AbstractListModel implements ComboBoxModel {

    Port.DATABITS[] data = new Port.DATABITS[]{
            Port.DATABITS.DB_6,
            Port.DATABITS.DB_7,
            Port.DATABITS.DB_8
    };

    Port.DATABITS selection = null;

    public Object getElementAt(int index) {
        return data[index];
    }

    public int getSize() {
        return data.length;
    }

    public void setSelectedItem(Object anItem) {
        selection = (Port.DATABITS) anItem;
    }

    public Object getSelectedItem() {
        return selection;
    }
}