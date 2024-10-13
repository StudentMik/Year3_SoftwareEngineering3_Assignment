import java.sql.*;
public class PrintTables
{
	static Connection con = null;
	static Statement stmt = null;
	static ResultSet rs = null;

	public static void main(String[] args) 
	{
		init_db();  // open the connection to the database
		try
		{
			rs = stmt.executeQuery("SELECT * FROM employees");
			System.out.println("Empolyee Results \n");
			while (rs.next()) {
				int empid = rs.getInt(1);
				String firstname = rs.getString("firstName");
				String lastname = rs.getString("lastName");
				String address1= rs.getString(4);
				String address2 = rs.getString(5);
				String town = rs.getString(6);
				String contactNo = rs.getString(7);
				String dateOfBirth = rs.getString(8);
				String gender = rs.getString(9);
				String position = rs.getString(10);
				double rate= rs.getDouble("rate");
				System.out.println(empid + " " + firstname + " " + lastname + " " + address1+ " " + address2 + " " + town + " " + contactNo + " " + dateOfBirth + " " + gender + " " + position + " " + rate);
			}
			
			rs = stmt.executeQuery("SELECT * FROM customers");
			System.out.println("\n Customer Results \n");
			while (rs.next()) {
				int cusid = rs.getInt(1);
				String firstname = rs.getString("firstName");
				String lastname = rs.getString("lastName");
				String address1= rs.getString(4);
				String address2 = rs.getString(5);
				String town = rs.getString(6);
				String contactNo = rs.getString(7);
				String dateOfBirth = rs.getString(8);
				String gender = rs.getString(9);
				System.out.println(cusid + " " + firstname + " " + lastname + " " + address1+ " " + address2 + " " + town + " " + contactNo + " " + dateOfBirth + " " + gender);
			}

		}
		catch (SQLException sqle)
		{
			System.out.println("Error: failed to get number of records");
		}
		try
		{
			con.close();
		}
		catch (SQLException sqle)
		{
			System.out.println("Error: failed to close the database");
		}
	}
	
	public static void init_db()
	{
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url="jdbc:mysql://localhost:3306/library?useTimezone=true&serverTimezone=UTC";
			con = DriverManager.getConnection(url, "root", "root");
			stmt = con.createStatement();
		}
		catch(Exception e)
		{
			System.out.println("Error: Failed to connect to database\n" + e.getMessage());
		}
	}
}