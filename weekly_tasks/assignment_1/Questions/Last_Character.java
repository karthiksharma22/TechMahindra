package assignments;
import java.util.Scanner;
public class Last_Character {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scan=new Scanner(System.in);
		String input=scan.nextLine();
		String s="";
		for(int i=0;i<input.length();i++) {
			if((input.charAt(i)==' ') && (i>0) && (Character.isLetter(input.charAt(i-1)))) {
				s=s+input.charAt(i-1);
			}
		}
			if(Character.isLetter(input.charAt(input.length()-1))) {
				s=s+input.charAt(input.length()-1);
				
			}
		System.out.println(s);
		scan.close();
	}

}
