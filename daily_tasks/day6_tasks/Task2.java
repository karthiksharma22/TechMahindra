package day6_tasks;
import java.sql.*;
import java.util.Scanner;

public class Task2 {

	public static void main(String[] args) throws SQLException,Exception {
		// TODO Auto-generated method stub
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/techm?autoReconnect=true&useSSL=false","root","tiger");
		
		String query = "Update Student set Name = ? where id = ?;";
		PreparedStatement st = con.prepareStatement(query);
		
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter the ID of the Student to Update their name");
		
		int id = scan.nextInt();
		st.setInt(2, id);
		
		System.out.println("Enter the new Name of the Student to update the Database");
		String new_name = scan.next();
		st.setString(1, new_name);
		
		int rows = st.executeUpdate();
		System.out.println("Rows effected : "+rows);
		
		System.out.println("Name updated Successfully");
		
		con.close();
		st.close();
		scan.close();
		
	}

}
