package day7_tasks;

import java.util.List;
import java.util.Scanner;
public class Task5 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<String> names = List.of("Abhiram","Aanad","Shyam","Madhaav","Keshav","Madhusudan");
		
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter the Letter to search for the names starting with that letter");
		String start_letter = scan.next();
		
		long number = names.stream().filter(e->e.startsWith(start_letter)).count();
		System.out.println("The number of names that start with letter "+start_letter+" are");
		System.out.println(number);
		scan.close();
	}

}
