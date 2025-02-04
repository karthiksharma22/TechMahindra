package day5_tasks.Collections;
import java.util.LinkedList;
import java.util.Scanner;

public class Task20 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		LinkedList<Integer> numbers = new LinkedList<>();
		numbers.add(1);
		numbers.add(4);
		numbers.add(2);
		numbers.add(6);
		numbers.add(3);
		numbers.add(6);
		
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter an element");
		int n = scan.nextInt();
		
		int findex = numbers.indexOf(n);
		int lindex = numbers.lastIndexOf(n);
		
		System.out.println("First occurance is "+findex);
		System.out.println("Last occurance is "+lindex);
		
		scan.close();
	}

}
