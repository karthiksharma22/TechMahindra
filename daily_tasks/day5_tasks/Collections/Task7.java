package day5_tasks.Collections;
import java.util.ArrayList;
import java.util.Scanner;

public class Task7 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayList<String> names = new ArrayList<>();
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter Names");
			
		while(true) {
			String name = scan.next();
			if(name.equals("stop")) break;
			else names.add(name);
		}
		
		System.out.println("Enter the name to search for index");
		String name = scan.next();
		int index = names.indexOf(name);
		if(index!=-1) System.out.println(name+" is at position "+index);
		else System.out.println("Name is not in the list");
		
		scan.close();
		
	}

}
