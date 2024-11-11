package Newsagent;

import java.util.Scanner;
import java.sql.*;

public class Customer 
{
    //-----------Customer Information Variables---------//
	
    private int id;             // Unique number for each customer
    private String name;        // Customer's name
    private String address;     // Customer's address
    private String phoneNumber; // Customer's phone number

    //-------Constructor to Create a New Customer------//
    
    // It checks if the name, address, and phone number are correct
    public Customer(String custName, String custAddress, String custPhone) throws CustomerExceptionHandler 
    {
        // Check the name, address, and phone number for errors
        validateName(custName);
        validateAddress(custAddress);
        validatePhoneNumber(custPhone);

        // Set the customer details
        this.id = 0;              // Set the customer ID to 0 (this can change later)
        this.name = custName;     // Store the name of the customer
        this.address = custAddress; // Store the address
        this.phoneNumber = custPhone; // Store the phone number
    }

    //-------Methods to Get and Set Customer Info-------//

    public int getId() 
    { 
        return id;  // Return the customer ID
    }

    public void setId(int id) 
    { 
        this.id = id;  // Set the customer ID to a new number
    }

    public String getName() 
    { 
        return name;  // Return the customer's name
    }

    public void setName(String name) 
    { 
        this.name = name;  // Set the customer's name
    }

    public String getAddress() 
    { 
        return address;  // Return the customer's address
    }

    public void setAddress(String address) 
    { 
        this.address = address;  // Set the customer's address
    }

    public String getPhoneNumber() 
    { 
        return phoneNumber;  // Return the customer's phone number
    }

    public void setPhoneNumber(String phoneNumber) 
    { 
        this.phoneNumber = phoneNumber;  // Set the customer's phone number
    }

    //--------Methods to Validate Customer Info-------//

    private void validateName(String custName) throws CustomerExceptionHandler 
    {
        // If the name is empty or too short or too long, it's not valid
        if (custName == null || custName.isBlank() || custName.length() < 2 || custName.length() > 50) 
        {
            throw new CustomerExceptionHandler("Name must be between 2 and 50 letters.");
        }
        // Name must only have letters, no numbers or special characters
        if (!custName.matches("[a-zA-Z]+")) 
        {
            throw new CustomerExceptionHandler("Name must only have letters.");
        }
    }

    private void validateAddress(String custAddress) throws CustomerExceptionHandler 
    {
        // If the address is too short or too long, it's not valid
        if (custAddress == null || custAddress.isBlank() || custAddress.length() < 5 || custAddress.length() > 60) 
        {
            throw new CustomerExceptionHandler("Address must be between 5 and 60 characters.");
        }
    }

    private void validatePhoneNumber(String custPhone) throws CustomerExceptionHandler 
    {
        // If the phone number is too short or too long, it's not valid
        if (custPhone == null || custPhone.isBlank() || custPhone.length() < 7 || custPhone.length() > 15) 
        {
            throw new CustomerExceptionHandler("Phone number must be between 7 and 15 characters.");
        }
    }

    //-------Add Customer to Database--------//
    
    public void addToDatabase(Connection con) throws SQLException 
    {
        // SQL command to add the customer to the database
        String str = "INSERT INTO customers (Name, Address, ContactNo) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = con.prepareStatement(str)) 
        {
            // Put the customer's name, address, and phone number into the SQL command
            pstmt.setString(1, this.name); 
            pstmt.setString(2, this.address); 
            pstmt.setString(3, this.phoneNumber);

            // Run the SQL command to save the customer in the database
            int rows = pstmt.executeUpdate(); 

            // If the customer is added, show a success message
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

    //--------Read Customers from Database-------//
    
    public static void readCustomer(Connection con) throws SQLException 
    {
        // SQL command to get all customers from the database
        String query = "SELECT * FROM customers";
        try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(query)) 
        {
            // Print the headers for the customer information
            System.out.println("ID\tName\t\tAddress\t\tPhone Number");
            System.out.println("--------------------------------------------");

            // Go through all customers and print their information
            while (rs.next()) 
            {
                int id = rs.getInt("id");
                String name = rs.getString("Name");
                String address = rs.getString("Address");
                String phone = rs.getString("ContactNo");

                // Print each customer's information
                System.out.println(id + "\t" + name + "\t\t" + address + "\t\t" + phone);
            }
        }
    }

    //-------Create a New Customer--------//
    
    public static void createCustomer(Connection con, Scanner in) 
    {
        try 
        {
            // Ask for customer details
            System.out.print("Please Enter the Name: ");
            String name = in.nextLine();
            System.out.print("Please Enter the Address: ");
            String address = in.nextLine();
            System.out.println("");	
            System.out.print("Please Enter the Contact Number: ");
            String contactNo = in.nextLine();

            // Create a new customer with the details
            Customer customer = new Customer(name, address, contactNo);
            // Add the new customer to the database
            customer.addToDatabase(con);
        } 
        catch (CustomerExceptionHandler e) 
        {
            // Show an error message if something is wrong
            System.out.println("Error: " + e.getMessage());
        } 
        catch (SQLException e) 
        {
            // Show an error message if there is a database problem
            System.out.println("SQL Error: " + e.getMessage());
        }
    }

    //-------Update Customer Information (Placeholder)--------//
    
    public void updateCustomer(Connection con) 
    {
        // This method will update the customer later
    }

    //------Delete Customer Information (Placeholder)-------//

    public void deleteCustomer(Connection con) 
    {
        // This method will delete the customer later
    }

    //---------Main Program-------//
    
    public static void main(String[] args) throws CustomerExceptionHandler 
    {
        Scanner in = new Scanner(System.in);
        Connection con = null;

        try 
        {
            con = init_db();  // Start the database connection

            // Show the options and ask the user to choose one
            System.out.println("Choose what you want to do:");
            System.out.println(" ");
            System.out.println("1. Create a new customer");
            System.out.println("2. Read customer");
            System.out.println("3. Update customer");
            System.out.println("4. Delete a customer");
            System.out.println(" ");
            System.out.print("Enter your choice: ");
            int choice = in.nextInt();
            in.nextLine(); // Clear input buffer

            // Do the action based on the user's choice
            switch (choice) 
            {
                case 1: 
                    createCustomer(con, in);  // Call the createCustomer method
                    break;

                case 2: 
                    readCustomer(con);        // Call the readCustomer method
                    break;

                case 3:
                    System.out.println("Update customer selected.");
                    Customer updateCustomer = new Customer("", "", "");
                    updateCustomer.updateCustomer(con);
                    break;

                case 4:
                    System.out.println("Delete customer selected.");
                    Customer deleteCustomer = new Customer("", "", "");
                    deleteCustomer.deleteCustomer(con);
                    break;

                default:
                    System.out.println("Invalid choice. Please select 1, 2, 3, or 4.");
                    break;
            }
        } 
        catch (SQLException e) 
        {
            // Show an error message if there is a problem with the database
            System.out.println("SQL Error: " + e.getMessage());
        } 
        finally 
        {
            // Close the database connection and scanner
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

    //---------Database Connection--------//
    
    public static Connection init_db() throws SQLException 
    {
        try 
        {
            // Load the database driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Database URL and credentials
            String url = "jdbc:mysql://localhost:3306/newsagent?useTimezone=true&serverTimezone=UTC";
            return DriverManager.getConnection(url, "root", "root");
        } 
        catch (ClassNotFoundException e) 
        {
            // If the database driver is not found, show an error
            throw new SQLException("Database driver not found: " + e.getMessage());
        }
    }
}

//---------Customer Exception Handler---------//

class CustomerExceptionHandler extends Exception 
{
    public CustomerExceptionHandler(String message) 
    {
        super(message);  // Show the error message
    }
}
