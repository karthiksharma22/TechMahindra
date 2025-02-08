package day7_tasks;
import java.util.List;

public class Task3 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<Integer> numbers = List.of(10,20,33,45,67,89,44);
		
		int sumEven = numbers.stream().filter(e->e%2==0).reduce(0, (a,b)->a+b);
		int sumOdd = numbers.stream().filter(e->e%2!=0).reduce(0, (a,b)->a+b);
		
		System.out.println("Sum of even numbers in list "+sumEven);
		System.out.println("Sum of odd numbers in list "+sumOdd);
	}

}
