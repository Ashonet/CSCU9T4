import java.io.*;

public class Simple_Java_XMLReader {
    public static void main(String[] args) {
        String fileName = "C:\\Users\\samue\\Documents\\Year_2_SPR\\CSCU9T4\\Practical_Ex\\Exercise_01\\Book_XML.xml";
        File file = new File(fileName);

        // Try to open and close the file
        try (FileReader fileReader = new FileReader(file)) {
            System.out.println("Opened file: " + fileName);
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + fileName);
        } catch (IOException e) {
            System.out.println("Error reading file: " + fileName);
        }
    }
}
