package day4_tasks.IO;
import java.io.InputStreamReader;
import java.io.BufferedReader;

public class Task7 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		InputStreamReader finput = new InputStreamReader(System.in);
		BufferedReader reader = new BufferedReader(finput);
		String name,branch,id;
		int age,exp;
		try {
			System.out.println("Enter Student Details");
			System.out.println("Name,branch,id");
			name = reader.readLine();
			branch = reader.readLine();
			id = reader.readLine();
			System.out.println("age and expirence");
			age = Integer.parseInt(reader.readLine());
			exp = Integer.parseInt(reader.readLine());
			
			System.out.println("Entered details");
			System.out.println("Name :"+name+" \nBranch :"+branch+" \nID:"+id);
			System.out.println("Age :"+age+" \nExpirence(years): "+exp);
			
		}
		 catch(Exception e) {
			System.err.println(e);
		}
				
	}

}
