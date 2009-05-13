package geproultra;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Lakatos Gyula
 * @version 1.0
 */
public class dumpPrices {

    public static String replaceString(String aInput, String aOldPattern, String aNewPattern) {
        return aInput.replace(aOldPattern, aNewPattern);
    }

    public int getItemNormalPrice(int itemId) throws MalformedURLException, IOException {
        URL url = new URL("http://itemdb-rs.runescape.com/viewitem.ws?obj=" + itemId);
        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
        String normal = "";
        String normal2 = "";

        String str;
        while ((str = in.readLine()) != null) {
            if (str.startsWith("<b>Market price:</b> ")) {
                normal = str.substring(21);
                normal2 = replaceString(normal, ".0k", "000");
                normal2 = replaceString(normal2, ".1k", "100");
                normal2 = replaceString(normal2, ".2k", "200");
                normal2 = replaceString(normal2, ".3k", "300");
                normal2 = replaceString(normal2, ".4k", "400");
                normal2 = replaceString(normal2, ".5k", "500");
                normal2 = replaceString(normal2, ".6k", "600");
                normal2 = replaceString(normal2, ".7k", "700");
                normal2 = replaceString(normal2, ".8k", "800");
                normal2 = replaceString(normal2, ".9k", "900");
                normal2 = replaceString(normal2, ".1m", "100000");
                normal2 = replaceString(normal2, ".2m", "200000");
                normal2 = replaceString(normal2, ".3m", "300000");
                normal2 = replaceString(normal2, ".4m", "400000");
                normal2 = replaceString(normal2, ".5m", "500000");
                normal2 = replaceString(normal2, ".6m", "600000");
                normal2 = replaceString(normal2, ".7m", "700000");
                normal2 = replaceString(normal2, ".8m", "800000");
                normal2 = replaceString(normal2, ".9m", "900000");
                normal2 = replaceString(normal2, ",", "");
                normal2 = replaceString(normal2, " ", "");
            }
        }

        return Integer.valueOf(normal2).intValue();
    }

    //Not used!
    public int getItemMinPrice(int itemId) throws MalformedURLException, IOException {
        URL url = new URL("http://itemdb-rs.runescape.com/viewitem.ws?obj=" + itemId);
        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
        String lowest = "";
        String lowest2 = "";

        String str;
        while ((str = in.readLine()) != null) {
            if (str.startsWith("<b>Minimum price:</b> ")) {
                lowest = str.substring(22);
                lowest2 = replaceString(lowest, ".0k", "000");
                lowest2 = replaceString(lowest2, ".1k", "100");
                lowest2 = replaceString(lowest2, ".2k", "200");
                lowest2 = replaceString(lowest2, ".3k", "300");
                lowest2 = replaceString(lowest2, ".4k", "400");
                lowest2 = replaceString(lowest2, ".5k", "500");
                lowest2 = replaceString(lowest2, ".6k", "600");
                lowest2 = replaceString(lowest2, ".7k", "700");
                lowest2 = replaceString(lowest2, ".8k", "800");
                lowest2 = replaceString(lowest2, ".9k", "900");
                lowest2 = replaceString(lowest2, ".1m", "100000");
                lowest2 = replaceString(lowest2, ".2m", "200000");
                lowest2 = replaceString(lowest2, ".3m", "300000");
                lowest2 = replaceString(lowest2, ".4m", "400000");
                lowest2 = replaceString(lowest2, ".5m", "500000");
                lowest2 = replaceString(lowest2, ".6m", "600000");
                lowest2 = replaceString(lowest2, ".7m", "700000");
                lowest2 = replaceString(lowest2, ".8m", "800000");
                lowest2 = replaceString(lowest2, ".9m", "900000");
                lowest2 = replaceString(lowest2, ",", "");
                lowest2 = replaceString(lowest2, " ", "");
            }
        }

        return Integer.valueOf(lowest2).intValue();
    }

    //Not used!
    public int getItemMaxPrice(int itemId) throws MalformedURLException, IOException, MalformedURLException, IOException {
        URL url = new URL("http://itemdb-rs.runescape.com/viewitem.ws?obj=" + itemId);
        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
        String highest = "";
        String highest2 = "";

        String str;
        while ((str = in.readLine()) != null) {
            if (str.startsWith("<b>Maximum price:</b> ")) {
                highest = str.substring(22);
                highest2 = replaceString(highest, ".0k", "000");
                highest2 = replaceString(highest2, ".1k", "100");
                highest2 = replaceString(highest2, ".2k", "200");
                highest2 = replaceString(highest2, ".3k", "300");
                highest2 = replaceString(highest2, ".4k", "400");
                highest2 = replaceString(highest2, ".5k", "500");
                highest2 = replaceString(highest2, ".6k", "600");
                highest2 = replaceString(highest2, ".7k", "700");
                highest2 = replaceString(highest2, ".8k", "800");
                highest2 = replaceString(highest2, ".9k", "900");
                highest2 = replaceString(highest2, ".1m", "100000");
                highest2 = replaceString(highest2, ".2m", "200000");
                highest2 = replaceString(highest2, ".3m", "300000");
                highest2 = replaceString(highest2, ".4m", "400000");
                highest2 = replaceString(highest2, ".5m", "500000");
                highest2 = replaceString(highest2, ".6m", "600000");
                highest2 = replaceString(highest2, ".7m", "700000");
                highest2 = replaceString(highest2, ".8m", "800000");
                highest2 = replaceString(highest2, ".9m", "900000");
                highest2 = replaceString(highest2, ",", "");
                highest2 = replaceString(highest2, " ", "");
            }
        }

        return Integer.valueOf(highest2).intValue();
    }

    public dumpPrices() throws ParserConfigurationException, IOException, SAXException {
        File file = new File("config/needToDump.xml");
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(file);
        doc.getDocumentElement().normalize();
        NodeList nodeLst = doc.getElementsByTagName("dump");

        BufferedWriter bw = new BufferedWriter(new FileWriter("config/PriceDB.xml"));

        bw.write("<?xml version=\"1.0\"?>");
        bw.newLine();
        bw.write("<pricedb>");
        bw.newLine();

        int itemId = 0;

        for (int s = 0; s < nodeLst.getLength(); s++) {
            Node fstNode = nodeLst.item(s);

            Element fstElmnt = (Element) fstNode;
            NodeList fstNmElmntLst = fstElmnt.getElementsByTagName("itemid");
            Element fstNmElmnt = (Element) fstNmElmntLst.item(0);
            NodeList itemIdElement = fstNmElmnt.getChildNodes();
            itemId = Integer.valueOf(((Node) itemIdElement.item(0)).getNodeValue()).intValue();

            bw.write("	<itemprice>");
            bw.newLine();
            bw.write("		<itemid>" + itemId + "</itemid>");
            bw.newLine();
            bw.write("		<medprice>" + getItemNormalPrice(itemId) + "</medprice>");
            bw.newLine();
            bw.write("	</itemprice>");
            bw.newLine();
        }
        bw.write("</pricedb>");
        bw.close();
    }
}
