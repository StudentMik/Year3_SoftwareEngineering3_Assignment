import java.sql.*;
import java.util.Scanner;

public class CreateInvoice {
    static Connection con = null;
    static Statement stmt = null;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        init_db(); // Open the connection to the database
        try {
            // SQL insert statement for the invoice table
            String str = "INSERT INTO invoice (OrderId, InvoiceDate, InvoiceTotal) VALUES (?, ?, ?)";

            // Get invoice details from the user
            System.out.println("Please Enter the Order ID:");
            int orderId = in.nextInt();
            in.nextLine(); // Consume the newline left-over
            System.out.println("Please Enter the Invoice Date (YYYY-MM-DD):");
            String invoiceDate = in.nextLine();
            System.out.println("Please Enter the Invoice Total:");
            double invoiceTotal = in.nextDouble();

            // Prepare the statement
            PreparedStatement pstmt = con.prepareStatement(str);
            pstmt.setInt(1, orderId);
            pstmt.setString(2, invoiceDate);
            pstmt.setDouble(3, invoiceTotal);

            // Execute the update
            int rows = pstmt.executeUpdate();

            // Check if the insert was successful
            if (rows > 0) {
                System.out.println("Invoice added successfully!");
            } else {
                System.out.println("Failed to add invoice.");
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
            String url = "jdbc:mysql://localhost:3306/News_Agent?useTimezone=true&serverTimezone=UTC";
            con = DriverManager.getConnection(url, "root", "root");
            stmt = con.createStatement();
        } catch (Exception e) {
            System.out.println("Error: Failed to connect to database\n" + e.getMessage());
        }
    }
}
