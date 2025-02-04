package day2;
import java.lang.StringBuilder;


public class task5 {
	public static void main(String args[]) {
		//String builder helps in creating a mutable string
		StringBuilder sb = new StringBuilder("anurag");
		System.out.println(sb);
		for(int i=0;i<sb.length();i+=2) {
			sb.setCharAt(i, Character.toUpperCase(sb.charAt(i)));
		}	
		System.out.println(sb);
	}
}
