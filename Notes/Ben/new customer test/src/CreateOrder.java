import java.sql.*;
import java.util.Scanner;

public class CreateOrder {
    static Connection con = null;
    static Statement stmt = null;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        init_db(); // Open the connection to the database
        try {
            // SQL insert statement for the orders table
            String str = "INSERT INTO orders (CustomerId, OrderTotalPrice, PublicationName) VALUES (?, ?, ?)";

            // Get order details from the user
            System.out.println("Please Enter the Customer ID:");
            int customerId = in.nextInt();
            System.out.println("Please Enter the Order Total Price:");
            double orderTotalPrice = in.nextDouble();
            in.nextLine(); // Consume the newline left-over
            System.out.println("Please Enter the Publication Name:");
            String publicationName = in.nextLine();

            // Prepare the statement
            PreparedStatement pstmt = con.prepareStatement(str);
            pstmt.setInt(1, customerId);
            pstmt.setDouble(2, orderTotalPrice);
            pstmt.setString(3, publicationName);

            // Execute the update
            int rows = pstmt.executeUpdate();

            // Check if the insert was successful
            if (rows > 0) {
                System.out.println("Order added successfully!");
            } else {
                System.out.println("Failed to add order.");
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
