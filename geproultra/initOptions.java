package geproultra;

import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author Lakatos Gyula
 */
public class initOptions extends JFrame {
    @SuppressWarnings("static-access")
    initOptions(){
        JFrame optFrame = new JFrame();
        JLabel showInfo = new JLabel("Show loading info in the tray icon?");

        optFrame.setSize(300, 300);
        optFrame.setResizable(false);
        optFrame.setTitle("Options");
        optFrame.setLocationRelativeTo(null);
        optFrame.setUndecorated(true);
        optFrame.setDefaultLookAndFeelDecorated(true);
        optFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        optFrame.add(showInfo);
        optFrame.setVisible(true);
    }
}
