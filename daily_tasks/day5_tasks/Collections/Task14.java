package day5_tasks.Collections;
import java.util.LinkedList;


public class Task14 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		LinkedList<Integer> numbers = new LinkedList<>();
		numbers.add(1);
		numbers.add(4);
		numbers.add(2);
		numbers.add(6);
		numbers.add(3);
		numbers.add(6);
			
		System.out.println("Traversing in reverse order");
		for(int i=numbers.size()-1;i>=0;i--) {
			System.out.printf("%d ", numbers.get(i));
		}
	}

}
