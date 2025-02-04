package day5_tasks.Collections;
import java.util.ArrayList;
import java.util.Scanner;

public class Task1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayList<String> colors = new ArrayList<>();
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter Colors");
		
		while(true) {
			String color = scan.next();
			if(color.equals("stop")) break;
			else colors.add(color);
		}
		
		System.out.println("Entered colors are ");
		for(int i=0;i<colors.size();i++) {
			System.out.println(colors.get(i));
		}
		
		scan.close();
	}

}
