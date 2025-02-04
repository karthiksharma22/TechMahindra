package day5_tasks.Collections;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Iterator;

public class Task19 {

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
		System.out.println("How many elements you want to add to linked list");
		int n = scan.nextInt();
		for(int i=0;i<n;i++) {
			System.out.println("Enter element and it's index");
			int value = scan.nextInt();
			int index = scan.nextInt();
			numbers.add(index,value);
		}
		
		System.out.println("Numbers are : ");
		Iterator<Integer> it2 = numbers.iterator();
		while(it2.hasNext()) {
			System.out.printf("%d ", it2.next());
		}
		scan.close();
	}

}
