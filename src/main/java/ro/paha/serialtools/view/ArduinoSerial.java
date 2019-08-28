package ro.paha.serialtools.view;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Properties;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import ro.paha.serialtools.Connector;
import ro.paha.serialtools.delimiter.Delimiter;
import ro.paha.serialtools.delimiter.LineFeed;
import ro.paha.serialtools.repository.Console;
import ro.paha.serialtools.repository.Repository;

/**
 * @author florin
 */
public class ArduinoSerial {

    private JFrame mainFrame;
    private Connector scm;
    private Repository repo;
    private Delimiter messageSeparator;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ArduinoSerial app = new ArduinoSerial();
                try {
                    System.out.println("begin");
                    app.begin();
                } catch (Exception e) {
                    e.printStackTrace();
                    System.exit(0);
                }
            }
        });
    }

    protected void begin() {
        scm = new Connector();
        repo = new Console();
        repo.open();
        messageSeparator = new LineFeed();
        mainFrame = setUpMainWindow();
    }

    /* Whenever user wishes to exit application stop logging, close port and release
     * any resources if occupied and gracefully exit. */
    public void appExit() {

        try {
            scm.closeAll();
        } catch (Exception e) {
        }
        repo.close();

        mainFrame.setVisible(false);
        mainFrame.dispose();
        System.exit(0);
    }

    private JFrame setUpMainWindow() {
        JFrame mainFrame = new JFrame("USB Serial logger");
        mainFrame.getContentPane().setLayout(new GridLayout(0, 1, 0, 0));
        mainFrame.setPreferredSize(new Dimension(715, 600));
        mainFrame.setResizable(false);

        // What will happen when user clicks on close icon.
        mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                appExit();
            }
        });

        JFrame.setDefaultLookAndFeelDecorated(true);
        try {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            JFrame.setDefaultLookAndFeelDecorated(true);
        }

        // When application starts, place window nearly in center of screen.
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        mainFrame.setLocation(d.width / 2 - 512, d.height / 2 - 384);

        MenuBar menuBar = new MenuBar(this);
        mainFrame.setJMenuBar(menuBar.getMenu());

        mainFrame.add(setUpMainPanel());
        mainFrame.pack();
        mainFrame.setVisible(true);

        return mainFrame;
    }

    private JPanel setUpMainPanel() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(0, 1, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 7, 5, 8));

        PortSettingsPanel portSettingsPanel = new PortSettingsPanel(scm, repo, messageSeparator);
        PortSettingsPanel portSettingsPanel2 = new PortSettingsPanel(scm, repo, messageSeparator);
        PortSettingsPanel portSettingsPanel3 = new PortSettingsPanel(scm, repo, messageSeparator);
        PortSettingsPanel portSettingsPanel4 = new PortSettingsPanel(scm, repo, messageSeparator);

        JTabbedPane jtp = new JTabbedPane();
        jtp.addTab("Device 1", portSettingsPanel.getPanel());
        jtp.addTab("Device 2", portSettingsPanel2.getPanel());
        jtp.addTab("Device 3", portSettingsPanel3.getPanel());
        jtp.addTab("Device 4", portSettingsPanel4.getPanel());
        mainPanel.add(jtp);

        return mainPanel;
    }
}
