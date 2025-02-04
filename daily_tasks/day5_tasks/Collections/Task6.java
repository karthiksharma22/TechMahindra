package day5_tasks.Collections;
import java.util.ArrayList;
import java.util.Iterator;

public class Task6 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		ArrayList<Integer> numbers = new ArrayList<>();
		numbers.add(1);
		numbers.add(2);
		numbers.add(3);
		numbers.add(4);
		numbers.add(5);
		numbers.add(6);
		
		numbers.remove(2);
		
		System.out.println("After removing 3rd element");
		
		Iterator<Integer> it = numbers.iterator();
		while(it.hasNext()) {
			System.out.println(it.next());
		}
		
	}

}
