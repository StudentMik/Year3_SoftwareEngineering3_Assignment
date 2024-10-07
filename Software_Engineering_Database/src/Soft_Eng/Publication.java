package Soft_Eng;

import java.sql.*;
import java.util.Scanner;

public class Publication {
	static Connection con = null;
	static Statement stmt = null;
	static ResultSet rs = null;

	public static void main(String[] args) {
		
		Scanner in = new Scanner(System.in);
		init_db(); // open the connection to the database
		try {
			
			String str = "INSERT INTO employees VALUES(null,?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			
			System.out.println("Please Enter the PublicationId");
			String PublicationId = in.next();
			
			System.out.println("Please Enter the PublicationName");
			String PublicationName = in.next();
			
			System.out.println("Please Enter the PublicationPrice");
			String PublicationPrice = in.next();
			
			System.out.println("Please Enter the Schedule");
			String Schedule = in.next();
			

			
			
			PreparedStatement pstmt = con.prepareStatement(str);
			pstmt.setString(1, PublicationId);
			pstmt.setString(2, PublicationName);
			pstmt.setString(3, PublicationPrice);
			pstmt.setString(4, Schedule);

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