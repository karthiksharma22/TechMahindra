package day4_tasks.Exceptions;
import java.util.Scanner;

class Example{
	public static int readInt(int n) throws Exception {	
		if(n%2==0) return n;
		else throw new Exception("Number should not be Odd");
	}
}

public class Task2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			System.out.println("Enter a number");
			Scanner scan = new Scanner(System.in);
			int n = scan.nextInt();
			scan.close();
			Example.readInt(n);
			System.out.println("The Number read is "+n);
		}
		catch(Exception e) {
			System.err.println("ERROR: "+e);
		}
	}

}
