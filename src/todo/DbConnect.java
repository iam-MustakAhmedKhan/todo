package todo;

import java.sql.*;


public class DbConnect {
	private Connection con;
	private Statement st;
	public DbConnect() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/todo","root","");
			 st = con.createStatement();
		} catch (Exception e) {
			System.out.print(e);
		}
	}
	public int registerUser(String insertQuery) {
		int a = 0;
		try {
			a = st.executeUpdate(insertQuery);
			
			st.close();
			con.close();
		} catch (Exception e) {
			System.out.print(e);
		}
		
		return a;
		
	}
	public ResultSet getUser(String searchQuery) {

		ResultSet rs = null;
		
		try {
			 rs = st.executeQuery(searchQuery);

		} catch (Exception e) {
			System.out.print(e);
		}
		
		return rs;
		
		
	}
}
