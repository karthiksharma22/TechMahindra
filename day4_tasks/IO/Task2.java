package day4_tasks.IO;
import java.io.File;
import java.util.Scanner;
public class Task2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			String filepath = "C:/Users/713so/eclipse-workspace/techm/src/day4_tasks/Exceptions";
			File directory = new File(filepath);
			File[] files = directory.listFiles();
			Scanner scan = new Scanner(System.in);
			System.out.println("Enter the extension");
			String extension = scan.next();
			for(File f:files) {
				String name = f.getName();
				if(name.endsWith(extension)) System.out.println(name);
			}
			scan.close();
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}
}
