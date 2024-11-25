package Newsagent;
import java.util.Scanner;
import java.sql.*;

public class Customer
{
    //-------------------------------------------Customer Information Variables-----------------------------------------//
    private int id;
    private String name;
    private String address;
    private String phoneNumber;

    //---------------------------------------Constructor to Create a New Customer--------------------------------------//
    public Customer(String custName, String custAddress, String custPhone) throws CustomerExceptionHandler
    {
        validateName(custName);
        validateAddress(custAddress);
        validatePhoneNumber(custPhone);

        this.id = 0;
        this.name = custName;
        this.address = custAddress;
        this.phoneNumber = custPhone;
    }

    //---------------------------------------Methods to Get and Set Customer Info------------------------------------//
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

    //----------------------------------------Methods to Validate Customer Info---------------------------------------//
    private void validateName(String custName) throws CustomerExceptionHandler
    {
        if (custName == null || custName.isBlank() || custName.length() < 2 || custName.length() > 50)
        {
            throw new CustomerExceptionHandler("Name must be between 2 and 50 letters.");
        }
        if (!custName.matches("[a-zA-Z]+ "))
        {
            throw new CustomerExceptionHandler("Name must only have letters.");
        }
    }

    private void validateAddress(String custAddress) throws CustomerExceptionHandler
    {
        if (custAddress == null || custAddress.isBlank() || custAddress.length() < 5 || custAddress.length() > 60)
        {
            throw new CustomerExceptionHandler("Address must be between 5 and 60 characters.");
        }
    }

    private void validatePhoneNumber(String custPhone) throws CustomerExceptionHandler
    {
        if (custPhone == null || custPhone.isBlank() || custPhone.length() < 7 || custPhone.length() > 15)
        {
            throw new CustomerExceptionHandler("Phone number must be between 7 and 15 characters.");
        }
    }

    //---------------------------------------Add Customer to Database----------------------------------------//
    public void addToDatabase(Connection con) throws SQLException
    {
        String str = "INSERT INTO customers (Name, Address, ContactNo) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = con.prepareStatement(str))
        {
            pstmt.setString(1, this.name);
            pstmt.setString(2, this.address);
            pstmt.setString(3, this.phoneNumber);
            int rows = pstmt.executeUpdate();

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

    //----------------------------------------Read Customers from Database---------------------------------------//
    public static void readCustomer(Connection con) throws SQLException
    {
        String query = "SELECT * FROM customers";
        try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(query))
        {
            System.out.println("ID\tName\t\tAddress\t\tPhone Number");
            System.out.println("--------------------------------------------");

            while (rs.next())
            {
                int id = rs.getInt("CustomerId");
                String name = rs.getString("Name");
                String address = rs.getString("Address");
                String phone = rs.getString("ContactNo");

                System.out.println(id + "\t" + name + "\t\t" + address + "\t\t" + phone);
            }
        }
    }

    //---------------------------------------Create a New Customer----------------------------------------//
    public static void createCustomer(Connection con, Scanner in)
    {
        try
        {
            System.out.print("Please Enter the Name: ");
            String name = in.nextLine();
            System.out.print("Please Enter the Address: ");
            String address = in.nextLine();
            System.out.println("");
            System.out.print("Please Enter the Contact Number: ");
            String contactNo = in.nextLine();

            Customer customer = new Customer(name, address, contactNo);
            customer.addToDatabase(con);
        }
        catch (CustomerExceptionHandler e)
        {
            System.out.println("Error: " + e.getMessage());
        }
        catch (SQLException e)
        {
            System.out.println("SQL Error: " + e.getMessage());
        }
    }

  //-----------------------------Update Customer Information--------------------------------------------//
    //update a customer
    public static void updateCustomer(Connection con, Scanner in)
    {
        try
        {
            System.out.print("Enter the Customer ID to update: ");
            int id = in.nextInt();
            in.nextLine(); // Clear the input buffer

            // Check if the customer exists
            String selectQuery = "SELECT * FROM customers WHERE CustomerId = ?";
            PreparedStatement pstmtSelect = con.prepareStatement(selectQuery);
            pstmtSelect.setInt(1, id);
            ResultSet rs = pstmtSelect.executeQuery();

            if (!rs.next())
            {
                System.out.println("No customer found with ID: " + id);
                return;
            }

            // Prompt user for new details
            System.out.print("Enter new Name: ");
            String newName = in.nextLine();

            System.out.print("Enter new Address: ");
            String newAddress = in.nextLine();

            System.out.print("Enter new Contact Number: ");
            String newContactNo = in.nextLine();

            // Update the customer details in the database
            String updateQuery = "UPDATE customers SET Name = ?, Address = ?, ContactNo = ? WHERE CustomerId = ?";
            PreparedStatement pstmtUpdate = con.prepareStatement(updateQuery);
            pstmtUpdate.setString(1, newName);
            pstmtUpdate.setString(2, newAddress);
            pstmtUpdate.setString(3, newContactNo);
            pstmtUpdate.setInt(4, id);

            int rows = pstmtUpdate.executeUpdate();
            System.out.println(rows > 0 ? "Customer updated successfully!" : "Failed to update customer.");

            // Close resources
            pstmtSelect.close();
            pstmtUpdate.close();

        }
        catch (SQLException e)
        {
            System.out.println("SQL Error: " + e.getMessage());
        }
    }

    //---------------------------------------Delete a New Customer----------------------------------------//
 // Delete a customer from the database
    public static void deleteCustomer(Connection con, Scanner in)
    {
        try
        {
            System.out.print("Enter the Customer ID to delete: ");
            int id = in.nextInt();

            String sql = "DELETE FROM customers WHERE CustomerId = ?";

            try (PreparedStatement pstmt = con.prepareStatement(sql))
            {
                pstmt.setInt(1, id);

                int rows = pstmt.executeUpdate();

                System.out.println(rows > 0 ? "Customer deleted successfully!" : "Customer not found or delete failed.");
            }
        }
        catch (SQLException e)
        {
            System.out.println("SQL Error: " + e.getMessage());
        }
    }

    //-----------------------------------------Main Program---------------------------------------//
    public static void main(String[] args) throws CustomerExceptionHandler, DeliveryExceptionHandler, InvoiceExceptionHandler, PublicationExceptionHandler, OrderExceptionHandler
    {
        Scanner in = new Scanner(System.in);
        Connection con = null;

        try
        {
            con = init_db();  // Start the database connection

            while (true)
            {  // Loop to allow multiple operations
                System.out.println("Choose what you want to do:");
                System.out.println("");
                System.out.println("1. Create a new customer");
                System.out.println("2. Read customers");
                System.out.println("3. Update a customer");
                System.out.println("4. Delete a customer");
                System.out.println("5. Exit");
                System.out.println("");
                System.out.print("Enter your choice: ");
                int choice = in.nextInt();
                in.nextLine(); // Clear input buffer

                switch (choice)
                {
                    case 1:
                        createCustomer(con, in);
                        break;
                    case 2:
                        readCustomer(con);
                        break;
                    case 3:
                        updateCustomer(con, in);
                        break;
                    case 4:
                        deleteCustomer(con, in);
                        break;
                    case 5:
                    	MainMenu.main(null);
                        System.out.println("Exiting program...");
                        break;  // Exit the program
                    default:
                        System.out.println("Invalid choice. Please select 1, 2, 3, 4, or 5.");
                        break;
                }

                // Ask the user if they want to perform another action
                System.out.print("Do you have more edits to customers? (yes/no): ");
                String answer = in.nextLine();
                if (answer.equalsIgnoreCase("no"))
                {
                    break;  // Exit the loop
                }
            }
        }
        catch (SQLException e)
        {
            System.out.println("SQL Error: " + e.getMessage());
        }
        finally
        {
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

    //-----------------------------------------Database Connection----------------------------------------//
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

//-----------------------------------------Customer Exception Handler-----------------------------------------//
class CustomerExceptionHandler extends Exception
{
    public CustomerExceptionHandler(String message)
    {
        super(message);
    }
}
