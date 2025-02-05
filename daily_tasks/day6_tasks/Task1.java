package day6_tasks;
import java.sql.*;
import java.util.Scanner;
public class Task1 {

	public static void main(String[] args) throws SQLException,Exception {
		
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/techm?autoReconnect=true&useSSL=false","root","tiger");
		
		String query = "Insert into Student values(?,?);";
		PreparedStatement st = con.prepareStatement(query);
		Scanner scan = new Scanner(System.in);
		boolean flag = true;
		while(flag) {
			System.out.println("Enter ID and Name of the new Student to insert");
			int id = scan.nextInt();
			String name = scan.next();
			st.setInt(1, id);
			st.setString(2, name);
			
			int rows = st.executeUpdate();
			
			System.out.println("Rows inserted : "+rows);
			System.out.println("Do you want to insert more? true/false");
			flag = scan.nextBoolean();
		}
		System.out.println("Student Records successfully added to the database");
		scan.close();
		st.close();
		con.close();
	}

}
