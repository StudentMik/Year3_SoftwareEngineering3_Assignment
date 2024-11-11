import java.sql.*;
import java.util.Scanner;

public class DeleteCustomers {
    static Connection con = null;
    static Statement stmt = null;
    static ResultSet rs = null;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        init_db(); // Open the connection to the database
        try {
            // Get customer name to delete
            System.out.println("Please Enter the Name of the customer to delete:");
            String name = in.nextLine();

            // SQL delete statement for the customers table
            String deleteStr = "DELETE FROM customers WHERE Name = ?";

            // Prepare the statement
            PreparedStatement pstmt = con.prepareStatement(deleteStr);
            pstmt.setString(1, name);

            // Execute the delete
            int rows = pstmt.executeUpdate();

            // Check if the delete was successful
            if (rows > 0) {
                System.out.println("Customer deleted successfully!");
            } else {
                System.out.println("Customer not found or delete failed.");
            }

        } catch (SQLException sqle) {
            System.out.println("Error: " + sqle.getMessage());
        } finally {
            // Close the database connection
            try {
                if (con != null) {
                    con.close();
                }
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
            stmt = con.createStatement();
        } catch (Exception e) {
            System.out.println("Error: Failed to connect to database\n" + e.getMessage());
        }
    }
}
