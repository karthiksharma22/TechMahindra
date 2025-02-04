package assignments;
import java.util.Scanner;
public class Reverse_NUM {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter Number to Reverse:");
		int number=scan.nextInt();
		int rev=0;
		while(number>0) {
			int digit=number%10;
			rev=rev*10+digit;
			number/=10;
		}
		System.out.println(rev);
		scan.close();
	}

}
