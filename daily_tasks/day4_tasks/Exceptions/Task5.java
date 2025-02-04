package day4_tasks.Exceptions;

import java.io.*;

public class Task5 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File file = new File("C:/Users/713so/eclipse-workspace/techm/src/day4_tasks/Exceptions/numbers.txt");
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            if (file.length() == 0) {
                throw new Exception("Error: File is empty");
            }
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
	}

}
