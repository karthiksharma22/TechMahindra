package day5_tasks.Collections;
import java.util.ArrayList;
import java.util.Scanner;

public class Task4 {

	public static void main(String[] args) {
		
		
		
		ArrayList<Integer> marks = new ArrayList<Integer>();
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter the Student Marks");
		while(true) {
			int n = scan.nextInt();
			if(n==-1) break;
			else marks.add(n);
		}
		
		System.out.println("Enter the index to get the details");
		int index = scan.nextInt();
		
		System.out.println("The marks of "+(index+1)+" student : "+marks.get(index));
		scan.close();

	}

}
