package day4_tasks.Exceptions;
import java.util.Scanner;
import java.io.File;

class PException extends Exception {
    public PException(String message) {
        super(message);
    }
}

public class Task4 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try  {
			String filepath = "C:/Users/713so/eclipse-workspace/techm/src/day4_tasks/Exceptions/";
			File f = new File(filepath);
			Scanner scanner = new Scanner(f);
            while (scanner.hasNext()) {
                if (scanner.hasNextInt()) {
                    int num = scanner.nextInt();
                    if (num > 0) {
                        throw new PException("Error: Positive number found - " + num);
                    }
                } else {
                    scanner.next(); 
                }
            }
            scanner.close();
            System.out.println("File read successfully without positive numbers.");
        } catch (Exception e) {
            System.err.println("File not found! "+e);
        } 
	}

}
