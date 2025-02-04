package day5_tasks.Collections;

import java.util.ArrayList;

public class Task9 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayList<Integer> numbers = new ArrayList<>();
		numbers.add(1);
		numbers.add(4);
		numbers.add(2);
		numbers.add(6);
		numbers.add(3);
		numbers.add(6);
		
		ArrayList<Integer> copy = new ArrayList<>();
		copy.addAll(numbers);
		
		for(int i=0;i<copy.size();i++)
			System.out.println(copy.get(i));
	}

}
