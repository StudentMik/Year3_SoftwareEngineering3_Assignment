package Newsagent; 
import java.sql.*;
import java.util.Scanner;
import java.text.*; 

public class Invoice
{
	private int orderId;
	//private int quantity;
	private String invoiceDate;
	private double invoiceTotal; 
	//private double pricePerItem; 
	//private String publicationName; 
	
	
//-----------Constructor----------//
	public Invoice(int orderId, String invoiceDate, double invoiceTotal) throws InvoiceExceptionHandler 
	{
		this.orderId = orderId;
		//this.quantity = quantity;
		this.invoiceDate = invoiceDate;
		this.invoiceTotal = invoiceTotal;
		//this.pricePerItem = pricePerItem;
		//this.publicationName = publicationName;
	}

	
//-----------Getters & Setters----------//
	public int getOrderId() 
	{
		return orderId;
	}
	public void setOrderId(int orderId) 
	{
		this.orderId = orderId;
	}

	/*public int getQuantity() 
	{
		return quantity;
	}
	public void setQuantity(int quantity) 
	{
		this.quantity = quantity;
	}*/

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

	/*public double getPricePerItem() 
	{
		return pricePerItem;
	}
	public void setPricePerItem(double pricePerItem) 
	{
		this.pricePerItem = pricePerItem;
	}

	public String getPublicationName() 
	{
		return publicationName;
	}
	public void setPublicationName(String publicationName) 
	{
		this.publicationName = publicationName;
	}*/
	
	
//----------Validation----------//
	public static void validateordId(int orderId) throws InvoiceExceptionHandler 
	{
		//String ordID = String.valueOf(orderId);
	    if(orderId <= 0) 
	    {
	        throw new InvoiceExceptionHandler("OrderId not specified or invalid");
	    }
	    if(orderId > 100) 
	    {
	        throw new InvoiceExceptionHandler("OrderId exceeds Maximum");
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
	    } catch (ParseException e) {
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
	
	
//----------Database----------//
	static Connection con = null;
    static Statement stmt = null;
    static ResultSet rs = null;

    public static void init_db() 
   	{
   		try 
		{
   			Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/NewsAgent?useTimezone=true&serverTimezone=UTC"; // Updated database name
            con = DriverManager.getConnection(url, "root", "Root");
        } 
    	catch (Exception e) 
		{
            System.out.println("Error: Failed to connect to database\n" + e.getMessage());
        }
    }
    	
    public static void close_db()
    {
    	try 
		{
    		if (con != null) 
    		{
    			con.close();
                System.out.println("Database Closed");
            }
        } 
        catch (SQLException sqle) 
    	{
        	System.out.println("Error: failed to close the database");
        }
    }
    
   	public static void main(String[] args) throws InvoiceExceptionHandler
	{
        Scanner in = new Scanner(System.in);
       	Invoice invoices = new Invoice(0, "01-02-2003", 12.99); 

        init_db(); // Open the connection to the database
        	
        System.out.println("Choose an Option:");
        System.out.println("1. Create a new Invoice");
       	System.out.println("2. View Invoices");
       	System.out.println("3. Exit");
       	System.out.print(": ");

       	int choice = in.nextInt();
       	in.nextLine();

        switch (choice) 
       	{
       		case 1:
       			invoices.addInvoice();
       			break;

        	case 2:
        		invoices.displayInvoices();
       			break;

       		case 3:
       			System.out.println("Exiting the program.");
       			in.close();
       			close_db();
       			return;
        				
       		default:
       			System.out.println("Error: Invalid Choice");
        }
	}

//----------Create: New Invoice----------//
   	public void addInvoice() throws InvoiceExceptionHandler
    {
   		Scanner in = new Scanner(System.in);
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
           	close_db();
        } 
        catch (SQLException sqle) 
		{
            System.out.println("Error: " + sqle.getMessage());
        } 	
    }
   	
   	
//----------Read: Display Invoices----------//
    public void displayInvoices() 
    {
    	try 
    	{
    		stmt = con.createStatement();
            String query = "SELECT * FROM invoice";
            rs = stmt.executeQuery(query);
            while (rs.next()) 
            {
            	System.out.println("Order Id: " + rs.getInt("orderId"));
            	System.out.println("Invoice Date: " + rs.getString("invoiceDate"));
            	System.out.println("Invoice Total: " + rs.getDouble("invoiceTotal"));
            	System.out.println("-------------------------------");
    		}
    		rs.close();
            stmt.close();
            close_db();
    	} 
    	catch (SQLException e) 
    	{
    			e.printStackTrace();
    	}
    }
}




