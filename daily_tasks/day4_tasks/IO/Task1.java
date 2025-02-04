package day4_tasks.IO;
import java.io.File;
public class Task1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			String filepath = "C:/Users/713so/eclipse-workspace/techm/src/day4_tasks/Exceptions";
			File directory = new File(filepath);
			File[] files = directory.listFiles();
			for(File f:files) {
				System.out.println(f.getName());
			}
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}

}
