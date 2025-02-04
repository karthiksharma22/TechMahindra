package day5_tasks.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

public class Task11 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		LinkedList<Integer> numbers = new LinkedList<>();
		Scanner scan = new Scanner(System.in);
		numbers.add(1);
		numbers.add(4);
		numbers.add(2);
		numbers.add(6);
		numbers.add(3);
		numbers.add(6);
		
		System.out.println("Enter element to add at the end of linked list");
		int n = scan.nextInt();
		numbers.addLast(n);
		
		System.out.println("Numbers are : ");
		Iterator<Integer> it2 = numbers.iterator();
		while(it2.hasNext()) {
			System.out.printf("%d ", it2.next());
		}
		scan.close();
	}

}
