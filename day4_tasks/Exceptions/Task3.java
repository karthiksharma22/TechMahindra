package day4_tasks.Exceptions;


import java.io.File;
import java.io.FileInputStream;
public class Task3 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String filepath="/src/day4_tasks/IO/Sample.txt";
		 try {
	            readfile(filepath);
	        } catch (Exception e) {
	            System.out.println("File not found - " + e.getMessage());
	        }
	}
	
	public static void readfile(String filep) throws Exception {
		File file=new File(filep);
		if (!file.exists()) {
	        throw new Exception("File does not exist: " + filep);
	    }
		else {
			System.out.println("File exists: " + filep);
			FileInputStream fis=null;	
		 }
		}

}
