package jdbc_demo;

import java.sql.*;
public class JDBCDemo {

	public static void main(String[] args) throws SQLException,Exception {
		// TODO Auto-generated method stub
		Class.forName("com.mysql.cj.jdbc.Driver");

		//Establishing connection with Connection class
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/techm?autoReconnect=true&useSSL=false","root","tiger");
		
		//creating a statement
		Statement stmt = con.createStatement();
		
		//execute the statement (select statement) and store the result in ResultSet
		ResultSet rs = stmt.executeQuery("select * from Student");
		
		//iterate the rows
		//the database consists of Student Table which contains id(int) and name(varchar)
		while(rs.next()) {
			//col 1 gets the student id
			System.out.println(rs.getInt(1));
			// col 2 gets the student name
			System.out.println(rs.getString(2));		
		}
		rs.close();
		stmt.close();
		con.close();
	   }
	}


