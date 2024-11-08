package Newsagent;

import java.util.Scanner;
import java.sql.*;

// This class represents a customer in the system
public class Customer 
{
    // Information about the customer
    private int id;             // Customer ID (starts as 0 by default)
    private String name;        // Customer name
    private String address;     // Customer address
    private String phoneNumber; // Customer phone number (stored as text)

    // Constructor to create a new customer with given details
    public Customer(String custName, String custAddress, String custPhone) throws CustomerExceptionHandler 
    {
        // Check if the provided name, address, and phone are valid
        validateName(custName); // Check if name is allowed
        validateAddress(custAddress); // Check if address is allowed
        validatePhoneNumber(custPhone); // Check if phone number is allowed

        // Set the information for this customer
        this.id = 0; // ID starts at 0 (can be changed later)
        this.name = custName; // Set the name
        this.address = custAddress; // Set the address
        this.phoneNumber = custPhone; // Set the phone number
    }

    // Get the customer ID
    public int getId() 
    {
        return id;
    }

    // Set the customer ID
    public void setId(int id) 
    {
        this.id = id;
    }

    // Get the customer name
    public String getName() 
    {
        return name;
    }

    // Set the customer name
    public void setName(String name) 
    {
        this.name = name;
    }

    // Get the customer address
    public String getAddress() 
    {
        return address;
    }

    // Set the customer address
    public void setAddress(String address) 
    {
        this.address = address;
    }

    // Get the customer phone number
    public String getPhoneNumber() 
    {
        return phoneNumber;
    }

    // Set the customer phone number
    public void setPhoneNumber(String phoneNumber) 
    {
        this.phoneNumber = phoneNumber;
    }

    // Check if the customer name is allowed
    private void validateName(String custName) throws CustomerExceptionHandler 
    {
        // Make sure name is not empty, not too short, not too long, and has only letters
        if (custName == null || custName.isBlank() || custName.length() < 2 || custName.length() > 50) 
        {
            throw new CustomerExceptionHandler("Invalid customer name: must be between 2 and 50 characters.");
        }

        if (!custName.matches("[a-zA-Z]+")) // Check for letters only
        {
            throw new CustomerExceptionHandler("Invalid customer name: must contain only letters.");
        }
    }

    // Check if the customer address is allowed
    private void validateAddress(String custAddress) throws CustomerExceptionHandler 
    {
        // Make sure address is not empty, not too short, and not too long
        if (custAddress == null || custAddress.isBlank() || custAddress.length() < 5 || custAddress.length() > 60) 
        {
            throw new CustomerExceptionHandler("Invalid customer address: must be between 5 and 60 characters.");
        }
    }

    // Check if the customer phone number is allowed
    private void validatePhoneNumber(String custPhone) throws CustomerExceptionHandler 
    {
        // Make sure phone number is not empty, not too short, and not too long
        if (custPhone == null || custPhone.isBlank() || custPhone.length() < 7 || custPhone.length() > 15) 
        {
            throw new CustomerExceptionHandler("Invalid customer phone number: must be between 7 and 15 characters.");
        }
    }

    // Add the customer to the database
    public void addToDatabase(Connection con) throws SQLException 
    {
        // SQL code to add customer information to the "customers" table
        String str = "INSERT INTO customers (Name, Address, ContactNo) VALUES (?, ?, ?)";

        // Prepare the SQL statement with customer information
        try (PreparedStatement pstmt = con.prepareStatement(str)) 
        {
            pstmt.setString(1, this.name); // Set name
            pstmt.setString(2, this.address); // Set address
            pstmt.setString(3, this.phoneNumber); // Set phone number

            // Run the SQL command to add the customer
            int rows = pstmt.executeUpdate();

            // If one or more rows were added, it means the customer was added successfully
            if (rows > 0) 
            {
                System.out.println("Customer added successfully!");
            } 
            else 
            {
                System.out.println("Failed to add customer.");
            }
        }
    }

    // Main method to test adding a customer
    public static void main(String[] args) 
    {
        Scanner in = new Scanner(System.in);
        Connection con = null;

        try 
        {
            // Start the database connection
            con = init_db();

            String name = ""; // Customer name
            String address = ""; // Customer address
            String contactNo = ""; // Customer phone number

            // Keep asking for customer details until they are valid
            while (true) 
            {
                try 
                {
                    // Ask user to enter customer details
                    System.out.println("Please Enter the Name:");
                    name = in.nextLine();
                    System.out.println("Please Enter the Address:");
                    address = in.nextLine();
                    System.out.println("Please Enter the Contact Number:");
                    contactNo = in.nextLine();

                    // Create a new customer with the details
                    Customer customer = new Customer(name, address, contactNo);
                    // Add customer to the database
                    customer.addToDatabase(con);
                    break; // Stop asking once customer is added
                } 
                catch (CustomerExceptionHandler e) 
                {
                    // If there is an error, show the message and ask again
                    System.out.println("Customer Error: " + e.getMessage());
                    System.out.println("Press Enter to retry...");
                    in.nextLine(); // Wait for user to hit Enter before retrying
                } 
            }
        } 
        catch (SQLException e) 
        {
            // Show error message if there is an issue with the database
            System.out.println("SQL Error: " + e.getMessage());
        } 
        finally 
        {
            // Close the database connection
            try 
            {
                if (con != null) 
                {
                    con.close();
                }
            } 
            catch (SQLException e) 
            {
                System.out.println("Error: failed to close the database");
            }
            in.close(); // Close the scanner
        }
    }

    // Connect to the database
    public static Connection init_db() throws SQLException 
    {
        try 
        {
            // Load the database driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // URL to connect to the database
            String url = "jdbc:mysql://localhost:3306/newsagent?useTimezone=true&serverTimezone=UTC";
            // Connect using the URL, username, and password
            return DriverManager.getConnection(url, "root", "root");
        } 
        catch (ClassNotFoundException e) 
        {
            // Show error if database driver is not found
            throw new SQLException("Database driver not found: " + e.getMessage());
        }
    }
}

// A custom exception for handling customer-related errors
class CustomerExceptionHandler extends Exception 
{
    public CustomerExceptionHandler(String message) 
    {
        super(message); // Send the error message to the parent Exception class
    }
}
