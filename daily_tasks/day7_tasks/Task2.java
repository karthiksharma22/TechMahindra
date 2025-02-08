package day7_tasks;

import java.util.List;
import java.util.stream.Stream;

public class Task2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		List<String> list = List.of("java","jdk","frontEnd","BackenD","mongoDB");
		
		Stream<String> result1 = list.stream().map(e->e.toUpperCase());
		System.out.println("\nAfter Converting to UpperCase");
		result1.forEach(e->System.out.print(e+" "));
		System.out.println();
		
		Stream<String> result2 = list.stream().map(e->e.toLowerCase());
		System.out.println("\nAfter Converting to LowerCase");
		result2.forEach(e->System.out.print(e+" "));
		System.out.println();
		
	}

}
