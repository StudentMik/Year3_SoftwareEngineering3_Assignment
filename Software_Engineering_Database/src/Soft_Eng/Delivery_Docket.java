package Soft_Eng;

import java.sql.*;
import java.util.Scanner;

public class Delivery_Docket {
	static Connection con = null;
	static Statement stmt = null;
	static ResultSet rs = null;

	public static void main(String[] args) {
		
		Scanner in = new Scanner(System.in);
		init_db(); // open the connection to the database
		try {
			
			String str = "INSERT INTO employees VALUES(null,?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

			System.out.println("Please Enter the DeliveryId");
			String DeliveryId = in.next();
			
			System.out.println("Please Enter the CustomerId");
			String CustomerId = in.next();
			
			System.out.println("Please Enter the OrderId");
			String orderId = in.next();
			
			System.out.println("Please Enter the address1");
			String PublicationId = in.next();
			
			System.out.println("Please Enter the Area");
			String PublicationName = in.next();
			
			System.out.println("Please Enter the contactNo");
			String DeliveryDate = in.next();
			
			
			PreparedStatement pstmt = con.prepareStatement(str);
			pstmt.setString(1, DeliveryId);
			pstmt.setString(3, CustomerId);
			pstmt.setString(3, orderId);
			pstmt.setString(4, PublicationId);
			pstmt.setString(5, PublicationName);
			pstmt.setString(6, DeliveryDate);

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