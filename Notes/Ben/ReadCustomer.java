import java.sql.*;

public class ReadCustomer {
    static Connection con = null;
    static Statement stmt = null;
    static ResultSet rs = null;

    public static void main(String[] args) {
        init_db(); // Open the connection to the database
        try {
            // SQL select statement for the customers table
            String str = "SELECT * FROM customers";

            // Execute the query
            stmt = con.createStatement();
            rs = stmt.executeQuery(str);

            // Display customer information
            System.out.println("Customer Information:");
            while (rs.next()) {
                int id = rs.getInt("CustomerId");
                String name = rs.getString("Name");
                String address = rs.getString("Address");
                String area = rs.getString("Area");
                String contactNo = rs.getString("ContactNo");

                System.out.println("ID: " + id + ", Name: " + name + ", Address: " + address + ", Area: " + area + ", Contact Number: " + contactNo);
            }

        } catch (SQLException sqle) {
            System.out.println("Error: " + sqle.getMessage());
        } finally {
            // Close the database connection
            try {
                if (con != null) con.close();
                if (stmt != null) stmt.close();
                if (rs != null) rs.close();
            } catch (SQLException sqle) {
                System.out.println("Error: failed to close the database");
            }
        }
    }

    public static void init_db() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/News_Agent?useTimezone=true&serverTimezone=UTC"; // Updated database name
            con = DriverManager.getConnection(url, "root", "root");
        } catch (Exception e) {
            System.out.println("Error: Failed to connect to database\n" + e.getMessage());
        }
    }
}
