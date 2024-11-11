package Newsagent;
import java.sql.*;
import java.util.Scanner;

public class Publications {
    static Connection con = null;// Database connection object
    static Statement stmt = null;// Statement object for executing queries
    static ResultSet rs = null;// ResultSet object for handling query results

 // Main method where the program execution starts
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in); // Scanner for user input
        init_db(); // Open the connection to the database
        boolean running = true;// Flag to keep the program running in a loop

     // Loop to display the menu and handle user input
        while (running) {
            System.out.println("Choose an Option:");
            System.out.println("1. Add a new publication");
            System.out.println("2. Display all publications");
            System.out.println("3. Modify a publication");
            System.out.println("4. Delete a publication");
            System.out.println("5. Exit");
            System.out.print(": ");
            int choice = in.nextInt(); // Read user's choice
            in.nextLine();  // Consume newline character left after integer input

            // Handle user's choice using switch statement
            switch (choice) {
                case 1:
                    addPublication(in); // Call addPublication method to add a new publication
                    break;
                case 2:
                    displayPublications(); // Call displayPublications method to show all publications
                    break;
                case 3:
                    modifyPublication(in); // Call modifyPublication method to modify an existing publication
                    break;
                case 4:
                    deletePublication(in); // Call deletePublication method to delete a publication
                    break;
                case 5:
                    running = false; // Exit the loop and end the program
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again."); // Handle invalid choice
            }
        }

        // Close the database connection
        try {
            if (con != null) {
                con.close(); // Close database connection
            }
            if (stmt != null) {
                stmt.close(); // Close statement object
            }
            if (rs != null) {
                rs.close(); // Close result set object
            }
        } catch (SQLException sqle) {
            System.out.println("Error: failed to close the database"); // Handle closing exceptions
        }
    }

 // Method to initialize the database connection
    public static void init_db() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");// Load MySQL JDBC driver
            String url = "jdbc:mysql://localhost:3306/News_Agent?useTimezone=true&serverTimezone=UTC"; // Database URL
            con = DriverManager.getConnection(url, "root", "root"); // Establish connection to the database
            stmt = con.createStatement(); // Create a statement object for executing SQL queries
        } catch (Exception e) {
            System.out.println("Error: Failed to connect to database\n" + e.getMessage()); // Handle connection errors
        }
    }

 // Method to add a new publication to the database
    public static void addPublication(Scanner in) {
        try {
        	// Prompt user for publication details
            System.out.println("Please Enter the Publication Name:");
            String name = in.nextLine(); // Read publication name

            System.out.println("Please Enter the Publication Price:");
            double price = in.nextDouble(); // Read publication price
            in.nextLine(); // Consume the newline character

            System.out.println("Please Enter the Schedule:");
            String schedule = in.nextLine(); // Read publication schedule

         // Validate the input schedule (Daily, Weekly, Monthly)
            validateschedule(schedule);

         // SQL query to insert a new publication into the database
            String insertStr = "INSERT INTO publication (PublicationName, PublicationPrice, Schedule) VALUES (?, ?, ?)";

         // Prepare statement to prevent SQL injection
            PreparedStatement pstmt = con.prepareStatement(insertStr);
            pstmt.setString(1, name); // Set publication name
            pstmt.setDouble(2, price); // Set publication price
            pstmt.setString(3, schedule); // Set publication schedule

         // Execute the insert query
            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Publication added successfully!"); // Success message
            } else {
                System.out.println("Failed to add the publication."); // Fail message
            }
            pstmt.close(); // Close the prepared statement
        } catch (SQLException sqle) {
            System.out.println("Error: " + sqle.getMessage()); // Handle SQL exceptions
        } catch (PublicationExceptionHandler e) {
            System.out.println("Error: " + e.getMessage()); // Handle custom exceptions
        }
    }

 // Method to display all publications from the database
    public static void displayPublications() {
        try {
        	// SQL query to select all publications from the database
            String str = "SELECT * FROM publication";
            rs = stmt.executeQuery(str); // Execute query and store results in ResultSet

         // Display all publications
            System.out.println("Publication Information:");
            while (rs.next()) {
                int id = rs.getInt("PublicationId"); // Get publication ID
                String name = rs.getString("PublicationName"); // Get publication name
                double price = rs.getDouble("PublicationPrice"); // Get publication price
                String schedule = rs.getString("Schedule"); // Get publication schedule

             // Print the details of each publication
                System.out.println("ID: " + id + ", Name: " + name + ", Price: " + price + ", Schedule: " + schedule);
            }
        } catch (SQLException sqle) {
            System.out.println("Error: " + sqle.getMessage()); // Handle SQL errors
        }
    }

 // Method to modify an existing publication's details
    public static void modifyPublication(Scanner in) {
        try {
        	// Ask user for the publication name they want to modify
            System.out.println("Please Enter the Name of the publication to modify:");
            String name = in.nextLine();

         // SQL query to find the publication by name
            String selectStr = "SELECT * FROM publication WHERE PublicationName = ?";

         // Prepare statement to execute the query
            PreparedStatement pstmtSelect = con.prepareStatement(selectStr);
            pstmtSelect.setString(1, name);

            // Execute the select query
            rs = pstmtSelect.executeQuery();

            // Check if the publication exists
            if (rs.next()) {
            	// Display the current details of the publication
                System.out.println("Publication found: " + name);
                System.out.println("Current details - Name: " + rs.getString("PublicationName")
                                   + ", Price: " + rs.getDouble("PublicationPrice")
                                   + ", Schedule: " + rs.getString("Schedule"));

                // Ask the user if they want to change the publication name
                System.out.println("Do you want to change the publication name? (yes/no):");
                String changeName = in.nextLine();

                if (changeName.equalsIgnoreCase("yes")) {
                	// If user wants to change the name, ask for the new name
                    System.out.println("Enter the new publication name:");
                    String newName = in.nextLine();

                 // SQL query to update the publication name
                    String updateNameStr = "UPDATE publication SET PublicationName = ? WHERE PublicationName = ?";
                    PreparedStatement pstmtUpdateName = con.prepareStatement(updateNameStr);
                    pstmtUpdateName.setString(1, newName); // Set new name
                    pstmtUpdateName.setString(2, name); // Set old name

                 // Execute the update query for changing the name
                    int rows = pstmtUpdateName.executeUpdate();
                    if (rows > 0) {
                        System.out.println("Publication name updated successfully!");
                        name = newName; // Update the local name variable to the new name
                    } else {
                        System.out.println("Failed to update the publication name.");
                    }
                    pstmtUpdateName.close(); // Close the prepared statement
                }

             // Ask user for new price and schedule
                System.out.println("Enter new price (current: " + rs.getDouble("PublicationPrice") + "):");
                double newPrice = in.nextDouble();
                in.nextLine(); // Consume the newline character

                // Prompt for new schedule
                System.out.println("Enter new schedule (current: " + rs.getString("Schedule") + "):");
                String newSchedule = in.nextLine();

                // Validate the new schedule
                validateschedule(newSchedule);

                // SQL update statement to change the price and schedule
                String updateStr = "UPDATE publication SET PublicationPrice = ?, Schedule = ? WHERE PublicationName = ?";

                // Prepare the update statement
                PreparedStatement pstmtUpdate = con.prepareStatement(updateStr);
                pstmtUpdate.setDouble(1, newPrice);
                pstmtUpdate.setString(2, newSchedule);
                pstmtUpdate.setString(3, name);

                // Execute the update
                int rows = pstmtUpdate.executeUpdate();

                // Check if the update was successful
                if (rows > 0) {
                    System.out.println("Publication updated successfully!");// Success message
                } else {
                    System.out.println("Failed to update publication.");// Failure message
                }
                pstmtUpdate.close(); // Close the prepared statement
            } else {
                System.out.println("Publication with name '" + name + "' not found."); // If publication does not exist
            }

        } catch (SQLException sqle) {
            System.out.println("Error: " + sqle.getMessage()); // Handle SQL exceptions
        } catch (PublicationExceptionHandler e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

 // Method to delete a publication from the database
    public static void deletePublication(Scanner in) {
        try {
        	// Ask the user for the name of the publication to delete
            System.out.println("Please Enter the Name of the publication to delete:");
            String name = in.nextLine();

         // SQL query to delete a publication by name
            String deleteStr = "DELETE FROM publication WHERE PublicationName = ?";

         // Prepare and execute the delete query
            PreparedStatement pstmt = con.prepareStatement(deleteStr);
            pstmt.setString(1, name);

         // Execute the delete query
            int rows = pstmt.executeUpdate();

            // Check if the delete was successful
            if (rows > 0) {
                System.out.println("Publication deleted successfully!"); // Success message
            } else {
                System.out.println("Publication not found or delete failed."); // Failure message
            }
            pstmt.close();
        } catch (SQLException sqle) {
            System.out.println("Error: " + sqle.getMessage());
        }
    }

    // Validate schedule input to ensure it's valid
    public static void validateschedule(String schedule) throws PublicationExceptionHandler {
        if (!(schedule.equalsIgnoreCase("Daily") || schedule.equalsIgnoreCase("Weekly") || schedule.equalsIgnoreCase("Monthly"))) {
            throw new PublicationExceptionHandler("Invalid schedule input. Daily, Weekly, or Monthly expected");
        }
    }
}
