package assignments;
import java.util.Scanner;


public class Perfect_Square {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scan = new Scanner(System.in);
		int number = scan.nextInt();
		double result = Math.sqrt(number);
		System.out.println(result == Math.floor(result));
		scan.close();
	}

}
