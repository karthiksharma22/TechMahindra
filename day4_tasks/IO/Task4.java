package day4_tasks.IO;
import java.io.File;
public class Task4 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File f = new File("src/day4_tasks/IO/Sample.txt");
		System.out.println("For the file : "+f.getName());
		System.out.println("Read Permissions : "+f.canRead());
		System.out.println("Write Permissions : "+f.canWrite());
	}

}
