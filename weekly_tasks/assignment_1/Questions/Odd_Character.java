package assignments;
import java.util.Scanner;

public class Odd_Character {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scan = new Scanner(System.in);
		String input = scan.nextLine();
		String output="";
		for(int i=0;i<input.length();i+=2) {
			 output+=input.charAt(i);
		}
		System.out.println(output);
		scan.close();
	}

}
