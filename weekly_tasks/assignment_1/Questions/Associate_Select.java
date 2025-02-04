package assignments;
import java.util.Scanner;

class Associate{
	int id,exp;
	String name,technology;
	Associate(int id,int exp, String name,String technology){
		this.id = id;
		this.exp = exp;
		this.name = name;
		this.technology = technology;
	}
}

class Solution{
	static Associate[] associatesForGivenTechnology(Associate[] array,String tech) {
		Associate[] newarray = new Associate[array.length];
		int j = 0;
		for(Associate i : array) {
			if(i.exp %5==0 && tech.equals(i.technology)) newarray[j++] = i;
		}
		return newarray;
	}
}

public class Associate_Select {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Scanner scan = new Scanner(System.in);
		
		System.out.println("Enter the number of Associates");
		int numemp = scan.nextInt();
		
		Associate[] array = new Associate[numemp];
		for(int i=0;i<numemp;i++) {
			System.out.println("Enter id, exp, name, technology");
			Associate a = new Associate(scan.nextInt(),scan.nextInt(),scan.next(),scan.next());
			
			array[i] = a;
		}
		System.out.println("Enter the technology to search for");
		String tech = scan.next();
		
		
		System.out.println("\nThe Result is");
		
		Associate[] result = Solution.associatesForGivenTechnology(array, tech);
		
		if(result[0] == null) {
			System.out.println("None Match"); 
		}
		for(Associate i : result) {
			if(i == null) break;
			System.out.println("The id: "+i.id);
		}
		scan.close();
		
	}

}
