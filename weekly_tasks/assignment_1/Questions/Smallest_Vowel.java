package assignments;
import java.util.Scanner;
public class Smallest_Vowel {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scan = new Scanner(System.in);
		String input = scan.nextLine().toLowerCase();
		char min_vowel = '{';
		
		for(int i=0;i<input.length();i++) {
			if(input.charAt(i)=='a' || input.charAt(i)=='e' || input.charAt(i)=='i' 
					|| input.charAt(i)=='o' || input.charAt(i)=='u') {
				
				if(input.charAt(i)<min_vowel) min_vowel = input.charAt(i);
			}
		}
		if(min_vowel==',') System.out.println("No Vowel present");
		else System.out.println(min_vowel);
		scan.close();
	}

}
