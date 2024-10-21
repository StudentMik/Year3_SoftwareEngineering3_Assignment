package Newsagent; 
import java.sql.*;
import java.util.Scanner;
import java.text.*; 

public class Invoice
{
	private int orderId;
	private int quantity;
	private String invoiceDate;
	private double invoiceTotal; 
	private double pricePerItem; 
	private String publicationName; 
	
	public Invoice(int orderId, int quantity, String invoiceDate, double invoiceTotal, double pricePerItem, String publicationName) throws InvoiceExceptionHandler 
	{
			validateId(orderId);
			validateinvoiceDate(invoiceDate);
			validateinvoiceTotal(invoiceTotal);
			
		this.orderId = orderId;
		this.quantity = quantity;
		this.invoiceDate = invoiceDate;
		this.invoiceTotal = invoiceTotal;
		this.pricePerItem = pricePerItem;
		this.publicationName = publicationName;
	}

	public int getOrderId() 
	{
		return orderId;
	}
	public void setOrderId(int orderId) 
	{
		this.orderId = orderId;
	}

	public int getQuantity() 
	{
		return quantity;
	}
	public void setQuantity(int quantity) 
	{
		this.quantity = quantity;
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

	public double getPricePerItem() 
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
	}
	
	
	public static void validateId(int orderId) throws InvoiceExceptionHandler {
	    if(orderId <= 0) 
	    {
	        throw new InvoiceExceptionHandler("OrderId not specified or invalid");
	    }
	    if(orderId > 200) 
	    {
	        throw new InvoiceExceptionHandler("OrderId exceeds Maximum Length");
	    }
	}
	public static void validateinvoiceDate(String invoiceDate) throws InvoiceExceptionHandler {
	    if (invoiceDate == null || invoiceDate.isEmpty()) {
	        throw new InvoiceExceptionHandler("Order date not specified or invalid");
	    }
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
	    dateFormat.setLenient(false); 
	    try {
	        dateFormat.parse(invoiceDate);
	    } catch (ParseException e) {
	        throw new InvoiceExceptionHandler("Order date is invalid, expected format is yyyy-mm-dd");
	    }
	}
	public static void validateinvoiceTotal(double invoiceTotal) throws InvoiceExceptionHandler 
	{
		if (invoiceTotal < 0) {
	        throw new InvoiceExceptionHandler("Total cannot be negative");
	    }
	}
	//
	static Connection con = null;
    static Statement stmt = null;

   	public static void main(String[] args) throws InvoiceExceptionHandler
	{
        	Scanner in = new Scanner(System.in);
        	init_db(); // Open the connection to the database
        	try {
            	// SQL insert statement for the invoice table
            	String str = "INSERT INTO invoice (OrderId, InvoiceDate, InvoiceTotal) VALUES (?, ?, ?)";

            	// Get invoice details from the user
            	System.out.println("Please Enter the Order ID:");
            	int orderId = in.nextInt();
            	validateId(orderId);
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
            	
        	} 
        	
        	catch (SQLException sqle) 
			{
            	System.out.println("Error: " + sqle.getMessage());
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
            catch (SQLException sqle) 
			{
                System.out.println("Error: failed to close the database");
            }
        }
    }

    public static void init_db() 
	{
        try 
		{
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/NewsAgent?useTimezone=true&serverTimezone=UTC";
            con = DriverManager.getConnection(url, "root", "Root");
            stmt = con.createStatement();
        } 
        catch (Exception e) 
		{
            System.out.println("Error: Failed to connect to database\n" + e.getMessage());
        }
    }
}


