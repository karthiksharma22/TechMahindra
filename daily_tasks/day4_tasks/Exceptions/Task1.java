package day4_tasks.Exceptions;
import java.util.Scanner;
public class Task1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scan = new Scanner(System.in);
		
		try {
			int a,b;
			System.out.println("Enter 2 Numbers");
			a = scan.nextInt();
			b = scan.nextInt();
			scan.close();
			if(b==0) throw new ArithmeticException("Cannot Divide by Zero");
			else System.out.println("Division result is "+(a/b));
		}
		catch(Exception e) {
			System.out.println("The error message is");
			System.err.println(e);
		}
		scan.close();
	}

}
