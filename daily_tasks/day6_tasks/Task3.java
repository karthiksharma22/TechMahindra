package day6_tasks;
import java.sql.*;
import java.util.Scanner;

public class Task3 {

	public static void main(String[] args) throws SQLException, Exception {
		// TODO Auto-generated method stub
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/techm?autoReconnect=true&useSSL=false","root","tiger");
		
		String query = "Delete from Student where id = ?;";
		PreparedStatement st = con.prepareStatement(query);
		
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter the Student ID to delete their record from the Database");
		int id = scan.nextInt();
		st.setInt(1, id);
		
		int rows = st.executeUpdate();
		System.out.println("Student Record Deleted Successfully with "+rows+" rows effected");
		
		scan.close();
		con.close();
		st.close();
		
	}

}
