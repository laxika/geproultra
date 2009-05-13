package geproultra;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.table.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Main extends JFrame {

    public static String projectLongName = "Grand Exchange Pro Ultra";
    public static String projectShortName = "GEPro Ultra";
    public static String projectVersion = "0.3b";
    
    public static JFrame frame = new JFrame();

    JTable table;
    DefaultTableModel model;
    JPanel buttonPanel;
    JButton button;

    public int getPrice(int itemId) throws SAXException, ParserConfigurationException, IOException {
        File file2 = new File("config/PriceDB.xml");
        DocumentBuilderFactory dbf2 = DocumentBuilderFactory.newInstance();
        DocumentBuilder db2 = dbf2.newDocumentBuilder();
        Document doc2 = db2.parse(file2);
        doc2.getDocumentElement().normalize();
        NodeList nodeLst2 = doc2.getElementsByTagName("itemprice");
        int itemId2 = 0;
        int itemMedPrice = 0;
        for (int s = 0; s < nodeLst2.getLength(); s++) {

            Node fstNode = nodeLst2.item(s);

            if (fstNode.getNodeType() == Node.ELEMENT_NODE) {
                Element fstElmnt = (Element) fstNode;

                NodeList fstNmElmntLst = fstElmnt.getElementsByTagName("itemid");
                Element fstNmElmnt = (Element) fstNmElmntLst.item(0);
                NodeList itemIdElement = fstNmElmnt.getChildNodes();

                NodeList list2 = fstElmnt.getElementsByTagName("medprice");
                fstNmElmnt = (Element) list2.item(0);
                NodeList itemNeedElement2 = fstNmElmnt.getChildNodes();

                itemId2 = Integer.valueOf(((Node) itemIdElement.item(0)).getNodeValue()).intValue();
                //System.out.println("READ! itemId2: " + itemId2 + " itemId: " + itemId);
                if (itemId2 == itemId) {
                    itemMedPrice = Integer.valueOf(((Node) itemNeedElement2.item(0)).getNodeValue()).intValue();
                }
            //System.out.println("READ! itemId2: "+itemId2+" medprice: "+itemMedPrice);
            }
        }

        return itemMedPrice;
    }

    public Main() throws ParserConfigurationException, SAXException, IOException {
        //Add the system tray

        //  Create table
        Object[][] data = {};

        int itemId = 0;
        int firstIndigents = 0;
        int secondIndigents = 0;
        int itemprice = 0;
        int firstprice = 0;
        int secondprice = 0;
        int resourceprice = 0;
        int profit = 0;
        String itemName = "";

        String[] columnNames = {"Picture", "Name", "Resources' price", "Profit", "Member", "Needed abilities"};
        model = new DefaultTableModel(data, columnNames) {

            @Override
            public Class getColumnClass(int column) {
                Class returnValue;
                if ((column >= 0) && (column < getColumnCount())) {
                    returnValue = getValueAt(0, column).getClass();
                } else {
                    returnValue = Object.class;
                }
                return returnValue;
            }
        };
        table = new JTable(model) {

            @Override
            public Class getColumnClass(int column) {
                return getValueAt(0, column).getClass();
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table.getColumnModel().getColumn(5).setCellRenderer(new TextAreaRenderer());
        RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
        table.setRowSorter(sorter);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
        TableColumn col0 = table.getColumnModel().getColumn(0);
        col0.setPreferredWidth(50);
        TableColumn col1 = table.getColumnModel().getColumn(1);
        col1.setPreferredWidth(200);
        TableColumn col2 = table.getColumnModel().getColumn(2);
        col2.setPreferredWidth(100);
        TableColumn col3 = table.getColumnModel().getColumn(3);
        col3.setPreferredWidth(50);
        TableColumn col4 = table.getColumnModel().getColumn(4);
        col4.setPreferredWidth(50);
        TableColumn col5 = table.getColumnModel().getColumn(5);
        col5.setPreferredWidth(300);

        table.setAutoCreateColumnsFromModel(false);

        table.setRowHeight(50);
        table.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);

        //  Add table and a Button panel to the frame

        JScrollPane scrollPane = new JScrollPane(table);
        //getContentPane().add(scrollPane);
        frame.add(scrollPane);

        File file = new File("config/tableConfig.xml");
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(file);
        doc.getDocumentElement().normalize();
        NodeList nodeLst = doc.getElementsByTagName("item");

        //Build up the table!
        for (int s = 0; s < nodeLst.getLength(); s++) {

            Node fstNode = nodeLst.item(s);

            if (fstNode.getNodeType() == Node.ELEMENT_NODE) {

                Element fstElmnt = (Element) fstNode;

                NodeList fstNmElmntLst = fstElmnt.getElementsByTagName("itemid");
                Element fstNmElmnt = (Element) fstNmElmntLst.item(0);
                NodeList itemIdElement = fstNmElmnt.getChildNodes();

                NodeList lstNmElmntLst = fstElmnt.getElementsByTagName("itemname");
                fstNmElmnt = (Element) lstNmElmntLst.item(0);
                NodeList itemNameElement = fstNmElmnt.getChildNodes();

                NodeList list1 = fstElmnt.getElementsByTagName("firstneed");
                fstNmElmnt = (Element) list1.item(0);
                NodeList itemNeedElement = fstNmElmnt.getChildNodes();

                NodeList list2 = fstElmnt.getElementsByTagName("secondneed");
                fstNmElmnt = (Element) list2.item(0);
                NodeList itemNeedElement2 = fstNmElmnt.getChildNodes();

                NodeList list3 = fstElmnt.getElementsByTagName("ability");
                fstNmElmnt = (Element) list3.item(0);
                NodeList itemAbilityElement = fstNmElmnt.getChildNodes();

                NodeList list4 = fstElmnt.getElementsByTagName("member");
                fstNmElmnt = (Element) list4.item(0);
                NodeList itemMemberElement = fstNmElmnt.getChildNodes();

                // add the new row to the table
                itemId = Integer.valueOf(((Node) itemIdElement.item(0)).getNodeValue()).intValue();
                itemName = ((Node) itemNameElement.item(0)).getNodeValue();
                //initTrayIcon.displayMessage("Tester!", "Some action performed", TrayIcon.MessageType.INFO);
                //initTrayIcon.trayIcon.displayMessage("Update the GE info", "Get info from " + itemName + "!", TrayIcon.MessageType.INFO);
                firstIndigents = Integer.valueOf(((Node) itemNeedElement.item(0)).getNodeValue()).intValue();
                secondIndigents = Integer.valueOf(((Node) itemNeedElement2.item(0)).getNodeValue()).intValue();
                itemprice = getPrice(itemId);
                firstprice = getPrice(firstIndigents);
                secondprice = getPrice(secondIndigents);
                resourceprice = firstprice + secondprice;
                profit = itemprice - resourceprice;
                System.out.println("Itemprice: " + itemprice + " First indigent: " + firstIndigents + " second: " + secondIndigents + " firstPrice: " + firstprice + " secondP: " + secondprice + " profit: " + profit);
                //ImageIcon icon = new ImageIcon("images/" + itemId + ".gif", "a pretty but meaningless splat");
                model.addRow(new Object[]{new ImageIcon("images/geicons/" + itemId + ".gif"), itemName, resourceprice, profit, ((Node) itemMemberElement.item(0)).getNodeValue(), ((Node) itemAbilityElement.item(0)).getNodeValue()});

            }

        }
    //frame.add(table);
    }

    static class ShowMessageListener implements ActionListener {

        TrayIcon trayIcon;
        String title;
        String message;
        TrayIcon.MessageType messageType;

        ShowMessageListener(
                TrayIcon trayIcon,
                String title,
                String message,
                TrayIcon.MessageType messageType) {
            this.trayIcon = trayIcon;
            this.title = title;
            this.message = message;
            this.messageType = messageType;
        }

        public void actionPerformed(ActionEvent e) {
            trayIcon.displayMessage(title, message, messageType);
        }
    }

    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, AWTException {
        final JFrame frame1 = new JFrame("Loading...");
        frame1.setUndecorated(true);
        //frame1.getContentPane().add(emptyLabel, BorderLayout.CENTER);
        frame1.setSize(288, 300);
        frame1.setLocationRelativeTo(null);
        ImageIcon icon = new ImageIcon("images/loading2.gif");
        JLabel label1 = new JLabel("Image and Text", icon, JLabel.CENTER);
        frame1.add(label1, BorderLayout.SOUTH);
//        JLabel loading = new JLabel("It is loading...");
//        frame1.add(loading, BorderLayout.SOUTH);

//Where the GUI is constructed:
        frame1.setVisible(true);
        dumpPrices initDumps = new dumpPrices();

        try {
            new initTrayIcon();
        } catch (AWTException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

        //final Main frame =
        new Main();
        frame.setDefaultCloseOperation(HIDE_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(761, 400);
        frame.setTitle(projectLongName+" "+projectVersion+" Created by Laxika");
        frame.setLocationRelativeTo(null);
        frame.setUndecorated(true);
        frame.setDefaultLookAndFeelDecorated(true);
        //frame.setIconImage(new ImageIcon(imgURL).getImage());
        frame.getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);

        //JFrame frame1 = new JFrame("Loading...");
        frame1.setVisible(false);
        frame.setVisible(true);
    }
}