package day4_tasks.IO;
import java.util.Scanner;
import java.io.File;
public class Task3 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			Scanner scan = new Scanner(System.in);
			String filepath = "C:/Users/713so/eclipse-workspace/techm/src/day4_tasks";
			System.out.println("Enter file path");
			String path = scan.next();
			filepath += path;
			File f = new File(filepath);
			System.out.println("File Exists? : "+f.exists());
			scan.close();		
		}catch(Exception e) {
			System.err.println(e);
		}
	}

}
