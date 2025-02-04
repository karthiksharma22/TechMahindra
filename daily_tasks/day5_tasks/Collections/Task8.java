package day5_tasks.Collections;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class Task8 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayList<Integer> numbers = new ArrayList<>();
		numbers.add(1);
		numbers.add(4);
		numbers.add(2);
		numbers.add(6);
		numbers.add(3);
		numbers.add(6);

		Iterator<Integer> it1 = numbers.iterator();
		System.out.println("Numbers before sorting");
		while(it1.hasNext()) {
			System.out.printf("%d ", it1.next());
		}
		
		System.out.println("\n\nNumbers after Sorting");
		Collections.sort(numbers);
		Iterator<Integer> it2 = numbers.iterator();
		while(it2.hasNext()) {
			System.out.printf("%d ", it2.next());
		}
	}

}
