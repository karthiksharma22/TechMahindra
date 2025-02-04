package day5_tasks.Collections;
import java.util.LinkedList;
import java.util.Scanner;

public class Task13 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		LinkedList<Integer> numbers = new LinkedList<>();
		numbers.add(1);
		numbers.add(4);
		numbers.add(2);
		numbers.add(6);
		numbers.add(3);
		numbers.add(6);
		
		System.out.println("Enter the index to start iteration from");
		Scanner scan = new Scanner(System.in);
		int index = scan.nextInt();
		
		for(int i=index;i<numbers.size();i++)
			System.out.printf("%d ", numbers.get(i));
		scan.close();
	}

}
