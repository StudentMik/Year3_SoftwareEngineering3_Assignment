import java.sql.*;
import java.util.Scanner;

public class AddEmployee2 {
	static Connection con = null;
	static Statement stmt = null;
	static ResultSet rs = null;

	public static void main(String[] args) {
		
		Scanner in = new Scanner(System.in);
		init_db(); // open the connection to the database
		try {
			
			String str = "INSERT INTO employees VALUES(null,?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

			System.out.println("Please Enter the FirstNAME");
			String firstname = in.next();
			System.out.println("Please Enter the LastName");
			String LastName = in.next();
			System.out.println("Please Enter the address1");
			String address1 = in.next();
			System.out.println("Please Enter the address2");
			String address2 = in.next();
			System.out.println("Please Enter the town");
			String town = in.next();
			System.out.println("Please Enter the contactNo");
			String contactNo = in.next();
			System.out.println("Please Enter the dateOfBirth");
			String dateOfBirth = in.next();
			System.out.println("Please Enter the gender");
			String gender = in.next();
			System.out.println("Please Enter the position");
			String position = in.next();
			System.out.println("Please Enter the rate");
			double rate = in.nextDouble();
			
			
			PreparedStatement pstmt = con.prepareStatement(str);
			pstmt.setString(1, firstname);
			pstmt.setString(2, LastName);
			pstmt.setString(3, address1);
			pstmt.setString(4, address2);
			pstmt.setString(5, town);
			pstmt.setString(6, contactNo);
			pstmt.setString(7, dateOfBirth);
			pstmt.setString(8, gender);
			pstmt.setString(9, position);
			pstmt.setDouble(10, rate);

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