package day4_tasks.IO;
import java.io.FileInputStream;
import java.io.IOException;

public class Task11 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String filePath = "src/day4_tasks/IO/Sample.txt";

        try  {
        	FileInputStream fis = new FileInputStream(filePath);
            
            int byteData;
            StringBuilder content = new StringBuilder();

            while ((byteData = fis.read()) != -1) {          
                content.append((char) byteData);
            }

            System.out.println(content.toString());
            fis.close();
        } catch (IOException e) {
            System.err.println("An error occurred while reading the file: " + e.getMessage());
        }
	}

}
