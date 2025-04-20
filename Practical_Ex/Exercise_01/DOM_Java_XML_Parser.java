import java.io.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;

public class DOM_Java_XML_Parser {
    public static void main(String[] args) {
        String fileName = "C:\\Users\\samue\\Documents\\Year_2_SPR\\CSCU9T4\\Practical_Ex\\Exercise_01\\Book_XML.xml";
        File file = new File(fileName);

        try {
            // Create a DocumentBuilderFactory
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(file);
            document.getDocumentElement().normalize();

            // Print all text content
            printTextContent(document.getDocumentElement());
        } catch (Exception e) {
            System.out.println("Error processing file: " + e.getMessage());
        }
    }

    private static void printTextContent(Node node) {
        NodeList nodeList = node.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node currentNode = nodeList.item(i);
            if (currentNode.getNodeType() == Node.TEXT_NODE) {
                String text = currentNode.getTextContent().trim();
                if (!text.isEmpty()) {
                    System.out.println(text);
                }
            } else {
                printTextContent(currentNode);
            }
        }
    }
}
