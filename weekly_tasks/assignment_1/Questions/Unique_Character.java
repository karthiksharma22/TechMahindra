package assignments;
import java.util.Scanner;
public class Unique_Character {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scan = new Scanner(System.in);
		String input,output="";
		input=scan.nextLine();
		
		for(int i =0;i<input.length();i++) {
			if(!output.contains(input.subSequence(i, i+1))) {
				output += input.subSequence(i, i+1);
			}
		}
		System.out.println(output);
		scan.close();
	}

}
