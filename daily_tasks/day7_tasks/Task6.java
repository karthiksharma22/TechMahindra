package day7_tasks;

import java.util.Comparator;
import java.util.List;

public class Task6 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		List<String> names = List.of("Shyam","Madhaav","Abhiram","Keshav","Madhusudan","Aanad","Sai");
		List<String> names_asc_sorted = names.stream().sorted().toList();
		List<String> names_desc_sorted = names.stream().sorted(Comparator.reverseOrder()).toList();
		
		System.out.println("Ascending order sorted : "+names_asc_sorted);
		System.out.println("Descending order sorted : "+names_desc_sorted);
	}

}
