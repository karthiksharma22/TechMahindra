package day7_tasks;

import java.util.List;
public class Task1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		List<Integer> list = List.of(11,24,35,43,52,60,76,85,99);
		
		double sum = list.stream()
					  .reduce(0, (a,b)->a+b);
		
		long count = list.stream().count();
		double average = sum/count;
		
		System.out.println("The average of the numbers of list "+list);
		System.out.printf("%.2f \n",average);
	}

}
