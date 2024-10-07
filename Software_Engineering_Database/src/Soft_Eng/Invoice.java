package Soft_Eng;

import java.sql.*;
import java.util.Scanner;

public class Invoice {
	static Connection con = null;
	static Statement stmt = null;
	static ResultSet rs = null;

	public static void main(String[] args) {
		
		Scanner in = new Scanner(System.in);
		init_db(); // open the connection to the database
		try {
			
			String str = "INSERT INTO employees VALUES(null,?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

			System.out.println("Please Enter the BillId");
			String BillId = in.next();
			
			System.out.println("Please Enter the CustomerId");
			String CustomerId = in.next();
			
			System.out.println("Please Enter the Publication1");
			String Publication1 = in.next();
			
			System.out.println("Please Enter the Publication2");
			String Publication2 = in.next();
			
			System.out.println("Please Enter the Publication3");
			String Publication3 = in.next();
			
			System.out.println("Please Enter the Publication4");
			String Publication4 = in.next();
			
			System.out.println("Please Enter the Publication5");
			String Publication5 = in.next();
			
			
			PreparedStatement pstmt = con.prepareStatement(str);
			pstmt.setString(1, BillId);
			pstmt.setString(3, CustomerId);
			pstmt.setString(7, Publication1);
			pstmt.setString(8, Publication2);
			pstmt.setString(9, Publication3);
			pstmt.setString(10, Publication4);
			pstmt.setString(11, Publication5);

			int rows = pstmt.executeUpdate();
			
			if (rows > 0)
			{
				System.out.println("Database updated");
			}

		} catch (SQLException sqle) {
			System.out.println("Error: failed to get number of records");
		}
		try {
			con.close();
		} catch (SQLException sqle) {
			System.out.println("Error: failed to close the database");
		}
	}

	public static void init_db() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/library?useTimezone=true&serverTimezone=UTC";
			con = DriverManager.getConnection(url, "root", "root");
			stmt = con.createStatement();
		} catch (Exception e) {
			System.out.println("Error: Failed to connect to database\n" + e.getMessage());
		}
	}
}