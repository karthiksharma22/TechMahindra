package assignments;
import java.lang.StringBuilder;
import java.util.Scanner;

public class Reverse_String {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scan = new Scanner(System.in);
		
		//method1
		StringBuilder sb = new StringBuilder(scan.nextLine());
		sb.reverse();
		System.out.println((sb.toString()).toLowerCase());
		
		
		//method2
		String input,output="";
		input = scan.nextLine();
		for(int i=input.length()-1;i>=0;i--) {
			output += input.charAt(i);
		}
		System.out.println(output.toLowerCase());
		scan.close();
		
	}

}
