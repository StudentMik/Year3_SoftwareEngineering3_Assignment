package Newsagent;
import java.sql.*;
import java.text.*;
import java.util.Scanner;

public class Deliveries
{
	private int driverID;
	private int deliveryDArea; //1-24
	private float deliveryDate;
	private int customerId;
	private int orderId;
	private String deliveryAddress;
	private String publicationName;  //publications delivered
	
	public Deliveries(int driverID, int deliveryDArea, float deliveryDate, int customerId, int orderId, String deliveryAddress, String publicationName) {
		this.driverID = driverID;
		this.deliveryDArea = deliveryDArea;
		this.deliveryDate = deliveryDate;
		this.customerId = customerId;
		this.orderId = orderId;
		this.deliveryAddress = deliveryAddress;
		this.publicationName = publicationName;
	}

	public int getDriverID() 
	{
		return driverID;
	}
	public void setDriverID(int driverID) 
	{
		this.driverID = driverID;
	}

	public int getDeliveryDArea() 
	{
		return deliveryDArea;
	}
	public void setDeliveryDArea(int deliveryDArea) 
	{
		this.deliveryDArea = deliveryDArea;
	}

	public float getDeliveryDate() 
	{
		return deliveryDate;
	}
	public void setDeliveryDate(int deliveryDate) 
	{
		this.deliveryDate = deliveryDate;
	}
	
	public int getCustomerId() 
	{
		return customerId;
	}
	public void setCustomerId(int customerId) 
	{
		this.customerId = customerId;
	}
	public int getOrderId() 
	{
		return orderId;
	}
	public void setOrderId(int orderId) 
	{
		this.orderId = orderId;
	}

	public String getDeliveryAddress() 
	{
		return deliveryAddress;
	}
	public void setDeliveryAddress(String deliveryAddress) 
	{
		this.deliveryAddress = deliveryAddress;
	}
	
	public String getPublicationName() {
		return publicationName;
	}
	public void setPublicationName(String publicationName) 
	{
		this.publicationName = publicationName;
	}

	public static void validatecustId(int customerId) throws DeliveryExceptionHandler 
	{
	    if(customerId <= 0) 
	    {
	        throw new DeliveryExceptionHandler("CustomerId not specified or invalid");
	    }
	    if(customerId > 100) 
	    {
	        throw new DeliveryExceptionHandler("CustomerId exceeds Maximum");
	    }
	}
	public static void validateordId(int orderId) throws DeliveryExceptionHandler 
	{
	    if(orderId <= 0) 
	    {
	        throw new DeliveryExceptionHandler("OrderId not specified or invalid");
	    }
	    if(orderId > 100) 
	    {
	        throw new DeliveryExceptionHandler("OrderId exceeds Maximum");
	    }
	}
	public static void validatepubliName(String publicationName) throws DeliveryExceptionHandler
	{
		if (publicationName.isBlank() || publicationName.isEmpty())
		{
			throw new DeliveryExceptionHandler("Publication Name not specified or invalid");
		}
		else if (publicationName.length() < 5)
		{
			throw new DeliveryExceptionHandler("Publication Name does not meet minimum length requirements");
		}
		else if (publicationName.length() > 50)
		{
			throw new DeliveryExceptionHandler("Publication Name does not exceeds maximum length requirements");
		}
		
	}
	public static void validatedeliveryDate(String deliveryDate) throws DeliveryExceptionHandler 
	{
	    if (deliveryDate == null || deliveryDate.isEmpty()) 
	    {
	        throw new DeliveryExceptionHandler("Invoice date not specified or invalid");
	    }
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
	    dateFormat.setLenient(false); 
	    try 
	    {
	        dateFormat.parse(deliveryDate);
	    } catch (ParseException e) {
	        throw new DeliveryExceptionHandler("Invoice date is invalid, expected format is yyyy-mm-dd");
	    }
	}
	
	//
		static Connection con = null;
    	static Statement stmt = null;

    	public static void main(String[] args) throws DeliveryExceptionHandler
	{
        	Scanner in = new Scanner(System.in);
        	init_db(); // Open the connection to the database
        	try 
			{
            	// SQL insert statement for the delivery_docket table
            	String str = "INSERT INTO delivery_docket (CustomerId, OrderId, PublicationName, DeliveryDate) VALUES (?, ?, ?, ?)";

            	// Get delivery docket details from the user
            	System.out.println("Please Enter the Customer ID:");
            	int customerId = in.nextInt();
            	validatecustId(customerId);
            	System.out.println("Please Enter the Order ID:");
            	int orderId = in.nextInt();
            	validateordId(orderId);
            	in.nextLine(); // Consume the newline left-over
            	System.out.println("Please Enter the Publication Name:");
            	String publicationName = in.nextLine();
            	validatepubliName(publicationName);
            	System.out.println("Please Enter the Delivery Date (YYYY-MM-DD):");
            	String deliveryDate = in.nextLine();
            	validatedeliveryDate(deliveryDate);
            	
            	// Prepare the statement
            	PreparedStatement pstmt = con.prepareStatement(str);
            	pstmt.setInt(1, customerId);
            	pstmt.setInt(2, orderId);
            	pstmt.setString(3, publicationName);
            	pstmt.setString(4, deliveryDate);

            	// Execute the update
            	int rows = pstmt.executeUpdate();

            	// Check if the insert was successful
            	if (rows > 0) 
		{
                	System.out.println("Delivery docket added successfully!");
            	} 
		else
		{
                	System.out.println("Failed to add delivery docket.");
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
