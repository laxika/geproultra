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

import org.jvnet.substance.skin.SubstanceBusinessBlackSteelLookAndFeel;

public class Main extends JFrame {

    public static String projectLongName = "Grand Exchange Pro Ultra";
    public static String projectShortName = "GEPro Ultra";
    public static String projectVersion = "0.3b";
    public static boolean debug = true;
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

                if (itemId2 == itemId) {
                    itemMedPrice = Integer.valueOf(((Node) itemNeedElement2.item(0)).getNodeValue()).intValue();
                }

            }
        }

        return itemMedPrice;
    }

    public Main() throws ParserConfigurationException, SAXException, IOException {
        super("GE Pro Ultra");

        this.setLayout(new BorderLayout());

        Object[][] data = new Object[100][6];

        int itemId = 0;
        int firstIndigents = 0;
        int secondIndigents = 0;
        int itemprice = 0;
        int firstprice = 0;
        int secondprice = 0;
        int resourceprice = 0;
        int profit = 0;
        String itemName = "";

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
                firstIndigents = Integer.valueOf(((Node) itemNeedElement.item(0)).getNodeValue()).intValue();
                secondIndigents = Integer.valueOf(((Node) itemNeedElement2.item(0)).getNodeValue()).intValue();
                itemprice = getPrice(itemId);
                firstprice = getPrice(firstIndigents);
                secondprice = getPrice(secondIndigents);
                resourceprice = firstprice + secondprice;
                profit = itemprice - resourceprice;
                data[s][0] = new ImageIcon("images/geicons/" + itemId + ".gif");
                data[s][1] = itemName;
                data[s][2] = resourceprice;
                data[s][3] = profit;
                data[s][4] = ((Node) itemMemberElement.item(0)).getNodeValue();
                data[s][5] = ((Node) itemAbilityElement.item(0)).getNodeValue();
                if (debug) {
                    System.out.println("Itemprice: " + itemprice + " First indigent: " + firstIndigents + " second: " + secondIndigents + " firstPrice: " + firstprice + " secondP: " + secondprice + " profit: " + profit);
                }
            }

        }

        String[] columnNames = {"Picture", "Name", "Resources' price", "Profit", "Member", "Needed abilities"};
        model = new DefaultTableModel(data, columnNames) {

            @Override
            public Class getColumnClass(int column) {
                Class returnValue;
                if ((column < getColumnCount())) {
                    returnValue = getValueAt(0, column).getClass();
                } else {
                    returnValue = Object.class;
                }
                return returnValue;
            }
        };
        table = new JTable(model) {

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

        JScrollPane scrollPane = new JScrollPane(table);
        this.add(scrollPane);

        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(HIDE_ON_CLOSE);
        this.setResizable(false);
        this.setSize(761, 400);
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
        dumpPrices initDumps = new dumpPrices();
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                try {
                    UIManager.setLookAndFeel(new SubstanceBusinessBlackSteelLookAndFeel());
                    JFrame.setDefaultLookAndFeelDecorated(true);
                    Main main = new Main();
                    main.setVisible(true);
                } catch (UnsupportedLookAndFeelException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ParserConfigurationException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SAXException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        try {
            new initTrayIcon();
        } catch (AWTException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
