package day5_tasks.Collections;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Iterator;

public class Task2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayList<String> names = new ArrayList<>();
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter Names");
		
		while(true) {
			String name = scan.next();
			if(name.equals("stop")) break;
			else names.add(name);
		}
		
		Iterator<String> it = names.iterator();		
		while(it.hasNext())
		{
			System.out.println(it.next());
		}		
		scan.close();
	}

}
