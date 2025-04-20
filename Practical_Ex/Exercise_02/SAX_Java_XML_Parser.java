import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;

public class SAX_Java_XML_Parser {
    public static void main(String[] args) {
        try {
            // Create a SAXParserFactory and configure it
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            
            // Specify the XML file to be parsed
            File xmlFile = new File("C:\\\\Users\\\\samue\\\\Documents\\\\Year_2_SPR\\\\CSCU9T4\\\\Practical_Ex\\\\Exercise_02\\\\receipts_full.xml");  // replace with the correct file path
            
            // Create a handler to handle SAX events
            DefaultHandler handler = new DefaultHandler() {
                
                // Flag to track the current element
                boolean isSalesDate = false;
                boolean isReceiptId = false;
                boolean isCashier = false;
                boolean isTimestamp = false;
                boolean isItemName = false;
                boolean isItemPrice = false;
                boolean isItemQuantity = false;
                boolean isItemSubtotal = false;
                boolean isPaymentType = false;
                boolean isPaymentAmount = false;
                
                // Start element event
                @Override
                public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                    // Handle "sales" element
                    if (qName.equalsIgnoreCase("sales")) {
                        String salesDate = attributes.getValue("date");
                        System.out.println("Sales Date: " + salesDate);
                    }
                    
                    // Handle "receipt" element
                    if (qName.equalsIgnoreCase("receipt")) {
                        String receiptId = attributes.getValue("id");
                        System.out.println("Receipt ID: " + receiptId);
                    }
                    
                    // Handle "item" element
                    if (qName.equalsIgnoreCase("item")) {
                        isItemName = false;
                        isItemPrice = false;
                        isItemQuantity = false;
                        isItemSubtotal = false;
                    }

                    // Handle item attributes if any (like "name", "price")
                    if (qName.equalsIgnoreCase("cashier")) {
                        isCashier = true;
                    }
                    if (qName.equalsIgnoreCase("timestamp")) {
                        isTimestamp = true;
                    }
                    if (qName.equalsIgnoreCase("name")) {
                        isItemName = true;
                    }
                    if (qName.equalsIgnoreCase("price")) {
                        isItemPrice = true;
                    }
                    if (qName.equalsIgnoreCase("quantity")) {
                        isItemQuantity = true;
                    }
                    if (qName.equalsIgnoreCase("subtotal")) {
                        isItemSubtotal = true;
                    }
                    if (qName.equalsIgnoreCase("payment")) {
                        isPaymentType = true;
                    }
                    if (qName.equalsIgnoreCase("amount")) {
                        isPaymentAmount = true;
                    }
                }
                
                // End element event
                @Override
                public void endElement(String uri, String localName, String qName) throws SAXException {
                    if (qName.equalsIgnoreCase("cashier")) {
                        isCashier = false;
                    }
                    if (qName.equalsIgnoreCase("timestamp")) {
                        isTimestamp = false;
                    }
                    if (qName.equalsIgnoreCase("name")) {
                        isItemName = false;
                    }
                    if (qName.equalsIgnoreCase("price")) {
                        isItemPrice = false;
                    }
                    if (qName.equalsIgnoreCase("quantity")) {
                        isItemQuantity = false;
                    }
                    if (qName.equalsIgnoreCase("subtotal")) {
                        isItemSubtotal = false;
                    }
                    if (qName.equalsIgnoreCase("payment")) {
                        isPaymentType = false;
                    }
                    if (qName.equalsIgnoreCase("amount")) {
                        isPaymentAmount = false;
                    }
                }
                
                // Characters event to capture the text content between elements
                @Override
                public void characters(char[] ch, int start, int length) throws SAXException {
                    String value = new String(ch, start, length).trim();
                    
                    if (isSalesDate) {
                        System.out.println("Sales Date: " + value);
                        isSalesDate = false;
                    }
                    if (isReceiptId) {
                        System.out.println("Receipt ID: " + value);
                        isReceiptId = false;
                    }
                    if (isCashier) {
                        System.out.println("Cashier: " + value);
                        isCashier = false;
                    }
                    if (isTimestamp) {
                        System.out.println("Timestamp: " + value);
                        isTimestamp = false;
                    }
                    if (isItemName) {
                        System.out.println("Item Name: " + value);
                        isItemName = false;
                    }
                    if (isItemPrice) {
                        System.out.println("Item Price: " + value);
                        isItemPrice = false;
                    }
                    if (isItemQuantity) {
                        System.out.println("Item Quantity: " + value);
                        isItemQuantity = false;
                    }
                    if (isItemSubtotal) {
                        System.out.println("Item Subtotal: " + value);
                        isItemSubtotal = false;
                    }
                    if (isPaymentType) {
                        System.out.println("Payment Type: " + value);
                        isPaymentType = false;
                    }
                    if (isPaymentAmount) {
                        System.out.println("Payment Amount: " + value);
                        isPaymentAmount = false;
                    }
                }
            };

            // Parse the XML file using the SAXParser and the handler
            saxParser.parse(xmlFile, handler);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}