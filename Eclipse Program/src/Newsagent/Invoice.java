package Newsagent; 
import java.sql.*;
import java.util.Scanner;
import java.text.*; 

public class Invoice
{
	//int quantity;double pricePerItem;String publicationName;
	private int orderId;  
	private String invoiceDate;
	private double invoiceTotal; 
	
	
//--------------------Constructor--------------------//
	public Invoice(int orderId, String invoiceDate, double invoiceTotal) throws InvoiceExceptionHandler 
	{
		this.orderId = orderId;
		this.invoiceDate = invoiceDate;
		this.invoiceTotal = invoiceTotal;
	}

	
//--------------------Getters & Setters--------------------//
	public int getOrderId() 
	{
		return orderId;
	}
	public void setOrderId(int orderId) 
	{
		this.orderId = orderId;
	}

	public String getInvoiceDate() 
	{
		return invoiceDate;
	}
	public void setInvoiceDate(String invoiceDate) 
	{
		this.invoiceDate = invoiceDate;
	}
	
	public double getInvoiceTotal() 
	{
		return invoiceTotal;
	}
	public void setInvoiceTotal(double invoiceTotal) 
	{
		this.invoiceTotal = invoiceTotal;
	}
	
	
//----------Database Connection--------------------//
    static Connection con = null;
    static Statement stmt = null;
    static ResultSet rs = null;

    public static void init_db() 
   	{
   		try 
		{
   			Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/NewsAgent?useTimezone=true&serverTimezone=UTC"; // Updated database name
            con = DriverManager.getConnection(url, "root", "root");
        } 
    	catch (Exception e) 
		{
            System.out.println("Error: Failed to connect to database\n" + e.getMessage());
        }
    }
    
    
//--------------------Main--------------------//
   	public static void main(String[] args) throws DeliveryExceptionHandler, InvoiceExceptionHandler, PublicationExceptionHandler, CustomerExceptionHandler, OrderExceptionHandler
	{
        Scanner in = new Scanner(System.in);
        boolean on = true;
        init_db(); // Open the connection to the database
        
        while (on)
        {
        	System.out.println("Choose an Option:");
        	System.out.println("1.	Create a new Invoice");
        	System.out.println("2.	View Invoices");
        	System.out.println("3. 	Update Invoice");
        	System.out.println("4.	Delete Invoice");
        	System.out.println("5.	Exit");
        	System.out.print(": ");

        	int choice = in.nextInt();
        	in.nextLine();

        	switch (choice) 
        	{
       			case 1:
       				addInvoice(in);
       				break;
       			case 2:
       				displayInvoices();
       				break;
       			case 3:
       				updateInvoice(in);
       				break;
       			case 4:
       				deleteInvoice(in);
       				break;
       			case 5:
       				System.out.println("Exiting Invoices....");
       				MainMenu.main(null);
       				return;
        				
       		default:
       			System.out.println("Error: Invalid Choice");
        	}
        }
        try 
        {
        	if (con != null) 
        	{
        		con.close(); // Close database connection
                System.out.println("fafaf");
            }
            if (stmt != null) 
            {
                stmt.close(); // Close statement object
            }
            if (rs != null) 
            {
                rs.close(); // Close result set object
            }
        } 
        catch (SQLException sqle) 
        {
        	System.out.println("Error: failed to close the database"); // Handle closing exceptions
        }
    }


//--------------------CREATE: Add an Invoice to the Database--------------------//
   	public static void addInvoice(Scanner in)
    {
        try 
        {
        	// SQL insert statement for the invoice table
            String str = "INSERT INTO invoice (OrderId, InvoiceDate, InvoiceTotal) VALUES (?, ?, ?)";

            // Get invoice details from the user
           	System.out.println("Please Enter the Order ID:");
           	int orderId = in.nextInt();
           	validateordId(orderId);
           	in.nextLine(); // Consume the newline left-over
           	System.out.println("Please Enter the Invoice Date (YYYY-MM-DD):");
           	String invoiceDate = in.nextLine();
           	validateinvoiceDate(invoiceDate);
           	System.out.println("Please Enter the Invoice Total:");
           	double invoiceTotal = in.nextDouble();
           	validateinvoiceTotal(invoiceTotal);
           	// Prepare the statement
            PreparedStatement pstmt = con.prepareStatement(str);
            pstmt.setInt(1, orderId);
           	pstmt.setString(2, invoiceDate);
           	pstmt.setDouble(3, invoiceTotal);

           	// Execute the update
           	int rows = pstmt.executeUpdate();

           	// Check if the insert was successful
           	if (rows > 0) 
           	{
               	System.out.println("Invoice added successfully!");
           	} 
           	else 
           	{
               	System.out.println("Failed to add invoice.");
           	}
           	pstmt.close();
        } 
        catch (SQLException sqle) 
		{
            System.out.println("Error: " + sqle.getMessage());
        }
        catch (InvoiceExceptionHandler e) {
            System.out.println("Error: " + e.getMessage()); // Handle custom exceptions
        }
    }
   	
   	
//--------------------READ: Display Invoices from the Database--------------------//
    public static void displayInvoices() 
    {
    	try 
    	{
    		stmt = con.createStatement();
            String query = "SELECT * FROM invoice";
            rs = stmt.executeQuery(query);
            while (rs.next()) 
            {
            	System.out.println("Invoie Id: " + rs.getInt("invoiceId"));
            	System.out.println("Order Id: " + rs.getInt("orderId"));
            	System.out.println("Invoice Date: " + rs.getString("invoiceDate"));
            	System.out.println("Invoice Total: €" + rs.getDouble("invoiceTotal"));
            	System.out.println("-------------------------------");
    		}
    	} 
    	catch (SQLException e) 
    	{
    		e.printStackTrace();
    	}
    }
    
    
//--------------------UPDATE: Change an Invoice in the Database--------------------//
    public static void updateInvoice(Scanner in)
    {
    	try 
    	{
        	// Ask user for the Invoice they want to modify
            System.out.println("Please Enter the InvoiceId of the Invoice to Update:");
            int invoiceId = in.nextInt();
            in.nextLine();

         // SQL query to find the Invoice by ID
            String selectStr = "SELECT * FROM Invoice WHERE InvoiceId = ?";

         // Prepare statement to execute the query
            PreparedStatement pstmtSelect = con.prepareStatement(selectStr);
            pstmtSelect.setInt(1, invoiceId);

            // Execute the select query
            rs = pstmtSelect.executeQuery();

            // Check if the Invoice exists
            if (rs.next()) 
            {
            	// Display the current details of the Invoice
                System.out.println("Invoice found ");
                System.out.println("Current details - InvoiceId: " + rs.getInt("InvoiceId")
                				   + ", OrderId: " + rs.getInt("OrderId")
                                   + ", Date: " + rs.getString("InvoiceDate")
                                   + ", Total: €" + rs.getDouble("InvoiceTotal"));

                // Ask the user if they want to change the Invoice
                System.out.println("Do you want to change this Invoice? (yes/no):");
                String changeInvoice = in.nextLine();

                if (changeInvoice.equalsIgnoreCase("yes")) 
                {
                	// Ask user for new OrderID
                	System.out.println("Enter new OrderId (current: " + rs.getInt("OrderId") + "): ");
                	int newOrderId = in.nextInt();
                	validateordId(newOrderId);
                	in.nextLine();
        
                	// Ask user for new Invoice Date
                	System.out.println("Enter new Date (current: " + rs.getString("InvoiceDate") + "):");
                	String newInvoiceDate = in.nextLine();
                	validateinvoiceDate(newInvoiceDate);
                	
                	// Ask user for new Invoice Total
                	System.out.println("Enter new Total (current: €" + rs.getDouble("InvoiceTotal") + "):");
                	double newinvoiceTotal = in.nextDouble();
                	in.nextLine();
                
                	// SQL update statement to Update the Invoice
                	String updateStr = "UPDATE invoice SET OrderId = ?, InvoiceDate = ?, InvoiceTotal = ? Where InvoiceId = ?";

                	// Prepare the update statement
                	PreparedStatement pstmtUpdate = con.prepareStatement(updateStr);
                	pstmtUpdate.setInt(1, newOrderId);
                	pstmtUpdate.setString(2, newInvoiceDate);
                	pstmtUpdate.setDouble(3, newinvoiceTotal);
                	pstmtUpdate.setInt(4, invoiceId);

                	// Execute the update
                	int rows1 = pstmtUpdate.executeUpdate();

                	// Check if the update was successful
                	if (rows1 > 0) 
                	{
                		System.out.println("Invoice updated successfully!");// Success message
                	} 
                	else 
                	{
                		System.out.println("Failed to update Invoice.");// Failure message
                	}
                	pstmtUpdate.close(); // Close the prepared statement
                } 
                else 
                {
                	System.out.println("Invoice with Id '" + invoiceId + "' not found."); // If Invoice does not exist
                }
            }   
    	} 
        catch (SQLException sqle) 
        {
        	System.out.println("Error: " + sqle.getMessage()); // Handle SQL exceptions
        } 
        catch (InvoiceExceptionHandler e) 
        {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    
//--------------------DELETE: Remove an Invoice from the Database--------------------//
    public static void deleteInvoice(Scanner in)
    {
    	try 
    	{
        	// Ask the user for the ID of the Invoice to delete
            System.out.println("Please Enter the Id of the Invoice to delete:");
            int invoiceId = in.nextInt();
            in.nextLine();
            
            String deleteStr = "DELETE FROM invoice WHERE InvoiceId = ?";

            // Prepare and execute the delete query
            PreparedStatement pstmt = con.prepareStatement(deleteStr);
            pstmt.setInt(1, invoiceId);
            int rows = pstmt.executeUpdate();

            // Check if the delete was successful
            if (rows > 0) 
            {
                System.out.println("Invoice deleted successfully!"); // Success message
            } 
            else 
            {
                System.out.println("Invoice not found or delete failed."); // Failure message
            }
            pstmt.close();
        } 
    	catch (SQLException sqle) 
    	{
            System.out.println("Error: " + sqle.getMessage());
        }
    }
    
    
  //--------------------Validation--------------------//
  	public static void validateordId(int orderId) throws InvoiceExceptionHandler 
  	{
  		//String ordID = String.valueOf(orderId);
  		if(orderId <= 0) 
  		{
  			throw new InvoiceExceptionHandler("OrderId not specified or invalid");
  		}
  		else if(orderId > 10000) 
  		{
  		throw new InvoiceExceptionHandler("OrderId exceeds Maximum");
  		}
  		
  		try 
  		{
  	        String checkQuery = "SELECT COUNT(*) FROM invoice WHERE OrderId = ?";
  	        PreparedStatement checkStmt = con.prepareStatement(checkQuery);
  	        checkStmt.setInt(1, orderId);
  	        ResultSet checkResult = checkStmt.executeQuery();
  	        checkResult.next();
  	        int count = checkResult.getInt(1);

  	        if (count > 0) 
  	        {
  	            throw new InvoiceExceptionHandler("Error: Duplicate Invoice Exists");
  	        }
  	        
  	        checkStmt.close();
  	    } 
  		catch (SQLException sqle) 
  		{
  			System.out.println("SQL Error: " + sqle.getMessage());
  	    }
  	}
  		
  	public static void validateinvoiceDate(String invoiceDate) throws InvoiceExceptionHandler 
  	{
  		if (invoiceDate == null || invoiceDate.isEmpty()) 
  		{
  			throw new InvoiceExceptionHandler("Invoice date not specified or invalid");
  		}
  		    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
  		    dateFormat.setLenient(false); 
  		    try 
  		    {
  		        dateFormat.parse(invoiceDate);
  		    } 
  		    catch (ParseException e) 
  		    {
  		        throw new InvoiceExceptionHandler("Invoice date is invalid, expected format is yyyy-mm-dd");
  		    }
  	}
  	
  	public static void validateinvoiceTotal(double invoiceTotal) throws InvoiceExceptionHandler 
  	{
  		if (invoiceTotal < 0) 
  		{
  			throw new InvoiceExceptionHandler("Total cannot be negative");
  		}
  	}
}




