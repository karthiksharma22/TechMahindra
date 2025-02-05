package day6_tasks;
import java.sql.*;
import java.util.Scanner;

public class Task4 {

	public static void main(String[] args) throws SQLException,Exception {
		
		// TODO Auto-generated method stub
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/techm?autoReconnect=true&useSSL=false","root","tiger");
		
		Scanner scan = new Scanner(System.in);
		String query = "";
		boolean flag = true;
		int rows = 0;
		while(flag) {
			System.out.println("Choose Your Choice\n1.Insert a new Row\n2.Update a row\n3.Delete a Row\n4.Select the Row\n5.Exit");
			int choice = scan.nextInt();
			switch(choice) {
			case 1: query = "Insert into Student values(?,?);";
					PreparedStatement st = con.prepareStatement(query);
					System.out.println("Enter ID and Name of the new Student to insert");
					int id = scan.nextInt();
					String name = scan.next();
					st.setInt(1, id);
					st.setString(2, name);
					rows = st.executeUpdate();
					System.out.println("Rows inserted : "+rows);
					break;
					
			case 2: query = "Update Student set Name = ? where id = ?;";
					PreparedStatement st2 = con.prepareStatement(query);
					System.out.println("Enter the ID of the Student to Update their name");
					int id2 = scan.nextInt();
					st2.setInt(2, id2);
					System.out.println("Enter the new Name of the Student to update the Database");
					String new_name = scan.next();
					st2.setString(1, new_name);		
					rows = st2.executeUpdate();
					System.out.println("Rows effected : "+rows);
					System.out.println("Name Updated Successfully");
					break;
					
			case 3: query = "Delete from Student where id = ?;";
				PreparedStatement st3 = con.prepareStatement(query);
				System.out.println("Enter the Student ID to delete their record from the Database");
				int id3 = scan.nextInt();
				st3.setInt(1, id3);	
				rows = st3.executeUpdate();
				System.out.println("Rows effected : "+rows);
				System.out.println("Record Deleted Successfully");
				break;
				
			case 4: query="Select * from Student where id = ?";
					PreparedStatement st4 = con.prepareStatement(query);
					System.out.println("Enter the Student ID to fetch their record");
					int id4 = scan.nextInt();
					st4.setInt(1, id4);	
					ResultSet rs = st4.executeQuery();
					System.out.println("ID    Name");
					while(rs.next()) {
						System.out.println(rs.getInt(1)+" "+rs.getString(2));
					}
					break;
			case 5: flag = false; break;
			default : System.out.println("Enter a valid option");
			}
		}
		
		scan.close();
		con.close();
		
	}

}
