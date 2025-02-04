package assignments;
import java.util.Scanner;

public class Space_Character_Count {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scan = new Scanner(System.in);
		String input = scan.nextLine();
		int spaces=0,chars=0;
		
		for(int i=0;i<input.length();i++) {
			if(!Character.isDigit(input.charAt(i)) && !Character.isWhitespace(input.charAt(i))) {
				chars++;
			}
			if(Character.isWhitespace(input.charAt(i))) {
				spaces++;
			}
		}
		System.out.println("No of spaces : "+spaces+" and characters : "+chars);
		scan.close();
	}

}
