package Newsagent;

import java.util.Scanner;
import java.sql.*;

// Custom exception class for customer-related errors
class CustomerExceptionHandler extends Exception 
{
    public CustomerExceptionHandler(String message) 
    {
        super(message); // Call the parent constructor with the error message
    }
}

public class Customer 
{
    // Instance variables to hold customer information
    private int id;
    private String name;
    private String address;
    private String phoneNumber; // Phone number is kept as String for flexibility

    // Constructor to initialize customer details
    public Customer(String custName, String custAddress, String custPhone) throws CustomerExceptionHandler 
    {
        // Validate customer details
        validateName(custName);
        validateAddress(custAddress);
        validatePhoneNumber(custPhone);

        // Set attributes after validation
        this.id = 0; // Default ID
        this.name = custName;
        this.address = custAddress;
        this.phoneNumber = custPhone;
    }

    // Getters and Setters
    public int getId() 
    {
        return id;
    }

    public void setId(int id) 
    {
        this.id = id;
    }

    public String getName() 
    {
        return name;
    }

    public void setName(String name) 
    {
        this.name = name;
    }

    public String getAddress() 
    {
        return address;
    }

    public void setAddress(String address) 
    {
        this.address = address;
    }

    public String getPhoneNumber() 
    {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) 
    {
        this.phoneNumber = phoneNumber;
    }

    // Method to validate customer name
    private void validateName(String custName) throws CustomerExceptionHandler 
    {
        // Check if the customer name is null, empty, or not within the character limit
        if (custName == null || custName.isBlank() || custName.length() < 2 || custName.length() > 50) 
        {
            throw new CustomerExceptionHandler("Invalid customer name: must be between 2 and 50 characters.");
        }

        // Check if the name contains only letters (using regex)
        if (!custName.matches("[a-zA-Z]+")) 
        {
            throw new CustomerExceptionHandler("Invalid customer name: must contain only letters.");
        }
    }

    // Method to validate customer address
    private void validateAddress(String custAddress) throws CustomerExceptionHandler 
    {
        if (custAddress == null || custAddress.isBlank() || custAddress.length() < 5 || custAddress.length() > 60) 
        {
            throw new CustomerExceptionHandler("Invalid customer address: must be between 5 and 60 characters.");
        }
    }

    // Method to validate customer phone number
    private void validatePhoneNumber(String custPhone) throws CustomerExceptionHandler 
    {
        if (custPhone == null || custPhone.isBlank() || custPhone.length() < 7 || custPhone.length() > 15) 
        {
            throw new CustomerExceptionHandler("Invalid customer phone number: must be between 7 and 15 characters.");
        }
    }

    // Method to add customer to the database
    public void addToDatabase(Connection con) throws SQLException 
    {
        // SQL insert statement for the customers table
        String str = "INSERT INTO customers (Name, Address, ContactNo) VALUES (?, ?, ?)";

        // Prepare the statement
        try (PreparedStatement pstmt = con.prepareStatement(str)) 
        {
            pstmt.setString(1, this.name);
            pstmt.setString(2, this.address);
            pstmt.setString(3, this.phoneNumber);

            // Execute the update
            int rows = pstmt.executeUpdate();

            // Check if the insert was successful
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

    // Main method for testing the Customer class
    public static void main(String[] args) 
    {
        Scanner in = new Scanner(System.in);
        Connection con = null;

        try 
        {
            // Initialize the database connection
            con = init_db();

            String name = "";
            String address = "";
            String contactNo = "";

            // Loop to get valid customer details
            while (true) 
            {
                try 
                {
                    // Get customer details from the user
                    System.out.println("Please Enter the Name:");
                    name = in.nextLine();
                    System.out.println("Please Enter the Address:");
                    address = in.nextLine();
                    System.out.println("Please Enter the Contact Number:");
                    contactNo = in.nextLine();

                    // Create a new customer object
                    Customer customer = new Customer(name, address, contactNo);
                    // Add customer to the database
                    customer.addToDatabase(con);
                    break; // Exit loop on successful addition
                } 
                catch (CustomerExceptionHandler e) 
                {
                    System.out.println("Customer Error: " + e.getMessage());
                    System.out.println("Press Enter to retry...");
                    in.nextLine(); // Wait for user to hit Enter before retrying
                } 
            }
        } 
        catch (SQLException e) 
        {
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
            in.close();
        }
    }

    // Method to initialize the database connection
    public static Connection init_db() throws SQLException 
    {
        try 
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/newsagent?useTimezone=true&serverTimezone=UTC";
            return DriverManager.getConnection(url, "root", "root");
        } 
        catch (ClassNotFoundException e) 
        {
            throw new SQLException("Database driver not found: " + e.getMessage());
        }
    }
}
