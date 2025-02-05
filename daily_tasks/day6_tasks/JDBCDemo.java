package jdbc_demo;

import java.sql.*;
public class JDBCDemo {

	public static void main(String[] args) throws SQLException,Exception {
		// TODO Auto-generated method stub
		Class.forName("com.mysql.cj.jdbc.Driver");
		//Establish the connection
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/techm?autoReconnect=true&useSSL=false","root","tiger");
		//enable the STatement object to pass sql queries.
		Statement stmt = con.createStatement();
		//use the ResultSet
		ResultSet rs = stmt.executeQuery("select * from Student");
		
		//iterate the rows
		while(rs.next()) {
			System.out.println(rs.getInt(1));
			System.out.println(rs.getString(2));		
		}
		rs.close();
		stmt.close();
		con.close();
	   }
	}


