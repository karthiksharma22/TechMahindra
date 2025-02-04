package day4_tasks.IO;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Task10 {

	public static void main(String[] args) {
		
		//read file content line by line
		String filePath = "src/day4_tasks/IO/Sample.txt";
		
        try  {
        	BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
    
            while ((line = reader.readLine()) != null) {
                System.out.println(line); 
            }
            reader.close();
        } catch (IOException e) {
            System.err.println("An error occurred while reading the file: " + e.getMessage());
        }
	}

}
