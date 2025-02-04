package day5_tasks.Collections;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Iterator;

public class Task5 {

	public static void main(String[] args) {
		
		// TODO Auto-generated method stub
		ArrayList<Integer> marks = new ArrayList<Integer>();
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter the Student Marks");
		while(true) {
			int n = scan.nextInt();
			if(n==-1) break;
			else marks.add(n);
		}
		
		System.out.println("Enter the index and new marks to update the list");
		int index = scan.nextInt();
		int newmarks = scan.nextInt();
		
		marks.set(index, newmarks);
		
		System.out.println("Marks after updating the list :");
		
		Iterator<Integer> it = marks.iterator();		
		while(it.hasNext())
		{
			System.out.println(it.next());
		}
		scan.close();
	}

}
