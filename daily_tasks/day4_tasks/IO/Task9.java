package day4_tasks.IO;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class Task9 {
    public static void main(String[] args) {
        
        String filePath = "src/day4_tasks/IO/Sample.txt";

        try {
            
            byte[] fileBytes = readFileToByteArray(filePath);
            ByteArrayInputStream bais = new ByteArrayInputStream(fileBytes);
            int data;
            while ((data = bais.read()) != -1) {
                System.out.print((char) data); 
            }
        } catch (IOException e) {
            System.err.println("An error occurred while reading the file: " + e.getMessage());
        }
    }

    public static byte[] readFileToByteArray(String filePath) throws IOException {
    	FileInputStream fis = new FileInputStream(filePath);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try 
        {

            byte[] buffer = new byte[1024]; 
            int bytesRead;    
            while ((bytesRead = fis.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesRead);
            }

            return baos.toByteArray();
            
        }finally {
        	fis.close();
        }
    }
}