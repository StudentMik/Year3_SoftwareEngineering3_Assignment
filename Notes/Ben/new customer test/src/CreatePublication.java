import java.sql.*;
import java.util.Scanner;

public class CreatePublication {
    static Connection con = null;
    static Statement stmt = null;
    static ResultSet rs = null;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        init_db(); // Open the connection to the database
        try {
            // SQL insert statement for the publication table
            String str = "INSERT INTO publication (PublicationName, PublicationPrice, Schedule) VALUES (?, ?, ?)";

            // Get publication details from the user
            System.out.println("Please Enter the Publication Name:");
            String publicationName = in.nextLine();  // Use nextLine() to capture full name
            System.out.println("Please Enter the Publication Price:");
            double publicationPrice = in.nextDouble();
            in.nextLine(); // Consume the newline left-over
            System.out.println("Please Enter the Schedule:");
            String schedule = in.nextLine();

            // Prepare the statement
            PreparedStatement pstmt = con.prepareStatement(str);
            pstmt.setString(1, publicationName);
            pstmt.setDouble(2, publicationPrice);
            pstmt.setString(3, schedule);

            // Execute the update
            int rows = pstmt.executeUpdate();

            // Check if the insert was successful
            if (rows > 0) {
                System.out.println("Publication added successfully!");
            } else {
                System.out.println("Failed to add publication.");
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
