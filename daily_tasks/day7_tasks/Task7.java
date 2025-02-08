package day7_tasks;

import java.util.List;

public class Task7 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<Integer> numbers = List.of(10,20,33,45,67,89,44);
		
		int base_value = numbers.get(0);
		int max_value = numbers.stream().reduce(base_value, (a,b)->a>b?a:b);
		int min_value = numbers.stream().reduce(base_value, (a,b)->a<b?a:b);
		
		System.out.println("The maximum of the List is : "+max_value);
		System.out.println("The minimum of the List is : "+min_value);
	}

}
