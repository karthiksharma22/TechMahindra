package day7_tasks;

import java.util.List;
import java.util.stream.Collectors;
public class Task4 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<Integer> numbers = List.of(1,1,2,2,3,3,4,4,5,6);
		List<Integer> distinct_numbers = numbers.stream().distinct()
										 .collect(Collectors.toList());
		
		System.out.println("Before removing duplicates : "+numbers);
		System.out.println("After removing duplicates : "+distinct_numbers);
	}

}
