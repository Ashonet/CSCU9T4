import java.io.*;
import javax.xml.parsers.*;
import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;

public class SAX_Java_XML_Parser {
    public static void main(String[] args) {
        String fileName = "C:\\Users\\samue\\Documents\\Year_2_SPR\\CSCU9T4\\Practical_Ex\\Exercise_01\\Book_XML.xml";
        File file = new File(fileName);

        try {
            // Create a SAXParserFactory
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();

            // Define a handler to process the XML content
            DefaultHandler handler = new DefaultHandler() {
                @Override
                public void characters(char[] ch, int start, int length) throws SAXException {
                    String text = new String(ch, start, length).trim();
                    if (!text.isEmpty()) {
                        System.out.println(text);
                    }
                }
            };

            // Parse the XML file
            saxParser.parse(file, handler);
        } catch (Exception e) {
            System.out.println("Error processing file: " + e.getMessage());
        }
    }
}