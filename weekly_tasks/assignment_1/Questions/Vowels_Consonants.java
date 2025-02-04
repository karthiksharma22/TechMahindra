package assignments;
import java.util.Scanner;


public class Vowels_Consonants {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scan = new Scanner(System.in);
		String input = scan.nextLine().toLowerCase();
		int v=0,c=0;
		for(int i=0;i<input.length();i++) {
			if(input.charAt(i)=='a' || input.charAt(i)=='e' || input.charAt(i)=='i' 
					|| input.charAt(i)=='o' || input.charAt(i)=='u') {
				v++;
			}
			else c++;
		}
		System.out.println("Vowels count - "+v+"\r\n"
				+ "Consonants count - "+c);
		scan.close();
	}

}
