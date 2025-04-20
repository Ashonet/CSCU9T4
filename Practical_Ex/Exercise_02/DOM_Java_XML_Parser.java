import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DOM_Java_XML_Parser {
    public static void main(String[] args) {
        try {
            // Create a DocumentBuilderFactory and configure it
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            
            // Load the XML file into a Document object
            File xmlFile = new File("C:\\Users\\samue\\Documents\\Year_2_SPR\\CSCU9T4\\Practical_Ex\\Exercise_02\\receipts_full.xml");  // replace with the correct file path
            Document document = builder.parse(xmlFile);
            
            document.getDocumentElement().normalize();
            
            // Print the root element's tag name to verify the document is loaded correctly
            System.out.println("Root element: " + document.getDocumentElement().getNodeName());
            
            // Example: Get the "sales" element and its "date" attribute
            Element salesElement = (Element) document.getElementsByTagName("sales").item(0);
            String salesDate = salesElement.getAttribute("date");
            System.out.println("Sales Date: " + salesDate);
            
            // Initialize variables to track the answers to your questions
            int receiptCount = 0;
            double totalSpentByJaneSmith = 0.0;
            Map<String, Integer> paymentMethods = new HashMap<>();
            Map<String, Integer> itemFrequency = new HashMap<>();
            Map<String, Double> itemTotalSpent = new HashMap<>();
            Map<String, Double> cashierSales = new HashMap<>();
            double mostExpensiveTransaction = 0.0;
            String mostExpensiveTransactionCashier = "";
            Map<String, Integer> itemPairFrequency = new HashMap<>();
            double totalCreditCardAndUSB = 0.0;
            
            // Get the receipt element
            NodeList receiptList = document.getElementsByTagName("receipt");
            for (int i = 0; i < receiptList.getLength(); i++) {
                Element receiptElement = (Element) receiptList.item(i);
                String receiptId = receiptElement.getAttribute("id");
                String cashier = receiptElement.getElementsByTagName("cashier").item(0).getTextContent();
                String timestamp = receiptElement.getElementsByTagName("timestamp").item(0).getTextContent();
                
                receiptCount++; // Count the receipts
                
                // Calculate total amount spent by cashier "Jane Smith"
                double totalAmount = 0.0;
                
                // Get all items in the receipt
                NodeList itemList = receiptElement.getElementsByTagName("item");
                for (int j = 0; j < itemList.getLength(); j++) {
                    Element item = (Element) itemList.item(j);
                    String itemName = item.getElementsByTagName("name").item(0).getTextContent();
                    double itemPrice = Double.parseDouble(item.getElementsByTagName("price").item(0).getTextContent());
                    int itemQuantity = Integer.parseInt(item.getElementsByTagName("quantity").item(0).getTextContent());
                    double itemSubtotal = Double.parseDouble(item.getElementsByTagName("subtotal").item(0).getTextContent());
                    
                    // Track total amount spent by cashier "Jane Smith"
                    if (cashier.equals("Jane Smith")) {
                        totalSpentByJaneSmith += itemSubtotal;
                    }
                    
                    // Track item frequency
                    itemFrequency.put(itemName, itemFrequency.getOrDefault(itemName, 0) + itemQuantity);
                    
                    // Track total amount spent on each item
                    itemTotalSpent.put(itemName, itemTotalSpent.getOrDefault(itemName, 0.0) + itemSubtotal);
                    
                    // Track most expensive transaction
                    totalAmount += itemSubtotal;
                    
                    // Track pair of items
                    for (int k = 0; k < itemList.getLength(); k++) {
                        if (j != k) { // Avoid pairing the same item
                            String itemName2 = ((Element) itemList.item(k)).getElementsByTagName("name").item(0).getTextContent();
                            String pairKey = itemName + " & " + itemName2;
                            itemPairFrequency.put(pairKey, itemPairFrequency.getOrDefault(pairKey, 0) + 1);
                        }
                    }
                }
                
                // Track payments in each receipt
                NodeList paymentList = receiptElement.getElementsByTagName("payment");
                for (int k = 0; k < paymentList.getLength(); k++) {
                    Element payment = (Element) paymentList.item(k);
                    String paymentType = payment.getElementsByTagName("type").item(0).getTextContent();
                    double paymentAmount = Double.parseDouble(payment.getElementsByTagName("amount").item(0).getTextContent());
                    
                    // Count payment types
                    paymentMethods.put(paymentType, paymentMethods.getOrDefault(paymentType, 0) + 1);
                    
                    // Track most expensive transaction
                    if (totalAmount > mostExpensiveTransaction) {
                        mostExpensiveTransaction = totalAmount;
                        mostExpensiveTransactionCashier = cashier;
                    }
                    
                    // Track credit card + USB Cable condition
                    if (paymentType.equals("Credit Card") && itemFrequency.containsKey("USB Cable")) {
                        totalCreditCardAndUSB += totalAmount;
                    }
                }
                
                // Track total sales by cashier
                cashierSales.put(cashier, cashierSales.getOrDefault(cashier, 0.0) + totalAmount);
            }

            // Now print out the results for each question:

            // 1. How many receipts were recorded on the given sales date?
            System.out.println("Receipts recorded on " + salesDate + ": " + receiptCount);
            
            // 2. Total amount spent by "Jane Smith" as cashier
            System.out.println("Total amount spent by cashier 'Jane Smith': " + totalSpentByJaneSmith);
            
            // 3. How many different types of payment methods are used?
            System.out.println("Different payment methods used: " + paymentMethods.size());
            
            // 4. Which item appears most frequently?
            String mostFrequentItem = itemFrequency.entrySet().stream()
                    .max(Map.Entry.comparingByValue())
                    .map(Map.Entry::getKey).orElse("None");
            System.out.println("Most frequently purchased item: " + mostFrequentItem);
            
            // 5. Total amount spent on "External Hard Drive" purchases
            System.out.println("Total spent on 'External Hard Drive': " + itemTotalSpent.getOrDefault("External Hard Drive", 0.0));
            
            // 6. Cashier with the highest total sales
            String highestSalesCashier = cashierSales.entrySet().stream()
                    .max(Map.Entry.comparingByValue())
                    .map(Map.Entry::getKey).orElse("None");
            System.out.println("Cashier with highest sales: " + highestSalesCashier + " with total: " + cashierSales.get(highestSalesCashier));
            
            // 7. Most expensive transaction and the cashier who processed it
            System.out.println("Most expensive transaction: " + mostExpensiveTransaction + " processed by " + mostExpensiveTransactionCashier);
            
            // 8. Most frequently purchased combination of two items
            String mostFrequentItemPair = itemPairFrequency.entrySet().stream()
                    .max(Map.Entry.comparingByValue())
                    .map(Map.Entry::getKey).orElse("None");
            System.out.println("Most frequently purchased combination of two items: " + mostFrequentItemPair);
            
            // 9. Total amount spent in receipts with "Credit Card" and "USB Cable"
            System.out.println("Total amount spent with 'Credit Card' and at least one 'USB Cable': " + totalCreditCardAndUSB);
            
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }
}
