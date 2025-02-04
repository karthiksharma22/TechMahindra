package day4_tasks.IO;

import java.io.File;
import java.util.Scanner;
import java.util.Date;

public class Task5_6_8 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String filepath = "src/day4_tasks/";
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter the required path");
		String path = scan.next();
		filepath += path;
		
		File f = new File(filepath);
		
		//checking file is directory or not
		if(f.isDirectory()) System.out.println(f.getName()+" is a directory");
		else System.out.println(f.getName()+" is a file");
		
		//getting the last modified date of the file 
		System.out.println(f.getName()+" is last modified at "+new Date(f.lastModified()));	
		
		//getting the file size in bytes, KB, MB
		System.out.println(f.getName()+" size in bytes is "+f.length());	
		System.out.println(f.getName()+" size in KB is "+(double)(f.length()/1024));
		System.out.println(f.getName()+" size in MB is "+(double)(f.length()/(1024*1024)));
		scan.close();
	}

}
