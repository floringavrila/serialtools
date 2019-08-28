package ro.paha.serialtools.view;

import ro.paha.serialtools.view.action.Exit;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

public final class MenuBar {

    private JMenuBar menubar;

    public MenuBar(ArduinoSerial parent) {
        menubar = new JMenuBar();
        /* File menu */
        JMenu file = new JMenu("File");
        file.setMnemonic(KeyEvent.VK_F);
        JMenuItem fileExit = new JMenuItem("Exit");
        fileExit.setMnemonic(KeyEvent.VK_C);
        fileExit.setToolTipText("Exit application");
        fileExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, ActionEvent.CTRL_MASK));
        fileExit.setAction(new Exit(parent));
        file.add(fileExit);
        menubar.add(file);
    }

    public JMenuBar getMenu() {
        return menubar;
    }
}