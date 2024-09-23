import java.sql.*;

public class AddEmployee {
	static Connection con = null;
	static Statement stmt = null;
	static ResultSet rs = null;

	public static void main(String[] args) {
		init_db(); // open the connection to the database
		try {
			String str = "INSERT INTO employees VALUES(null,?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

			PreparedStatement pstmt = con.prepareStatement(str);
			pstmt.setString(1, "Joe");
			pstmt.setString(2, "Mullins");
			pstmt.setString(3, "Big Long Road");
			pstmt.setString(4, "null");
			pstmt.setString(5, "Athlone");
			pstmt.setString(6, "08712345678");
			pstmt.setString(7, "1980-12-21");
			pstmt.setString(8, "M");
			pstmt.setString(9, "Manager");
			pstmt.setDouble(10, 24.99);

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