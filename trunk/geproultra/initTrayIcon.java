package geproultra;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Laxika
 * @version 1.0
 */
public class initTrayIcon {

    public static Image image = Toolkit.getDefaultToolkit().getImage("images/tray.gif");
    public static TrayIcon trayIcon = new TrayIcon(image, Main.projectShortName);

    public initTrayIcon() throws AWTException {
        if (SystemTray.isSupported()) {
            PopupMenu popup = new PopupMenu();
            SystemTray tray = SystemTray.getSystemTray();

            trayIcon.setImageAutoSize(true);

            MenuItem item = new MenuItem("Show");
            popup.add(item);
            item.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    Main.frame.setVisible(true);
                }
            });

            MenuItem options = new MenuItem("Options");
            popup.add(options);
            options.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    Options options = new Options();
                }
            });

            MenuItem closeItem = new MenuItem("Exit");
            popup.add(closeItem);
            closeItem.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    System.exit(0);
                }
            });

            trayIcon.setPopupMenu(popup);
            trayIcon.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    trayIcon.displayMessage(Main.projectLongName, "Welcome the " + Main.projectLongName + "!", TrayIcon.MessageType.INFO);
                }
            });

            try {
                tray.add(trayIcon);
            } catch (AWTException e) {
                System.err.println("TrayIcon could not be added.");
            }
        }
    }
}
