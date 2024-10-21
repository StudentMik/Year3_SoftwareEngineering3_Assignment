package Newsagent;
import java.sql.*;
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

	//
	static Connection con = null;
    	static Statement stmt = null;

    	public static void main(String[] args) 
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
            	System.out.println("Please Enter the Order ID:");
            	int orderId = in.nextInt();
            	in.nextLine(); // Consume the newline left-over
            	System.out.println("Please Enter the Publication Name:");
            	String publicationName = in.nextLine();
            	System.out.println("Please Enter the Delivery Date (YYYY-MM-DD):");
            	String deliveryDate = in.nextLine();

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
            	con = DriverManager.getConnection(url, "root", "root");
            	stmt = con.createStatement();
        	} 
		catch (Exception e) 
		{
            	System.out.println("Error: Failed to connect to database\n" + e.getMessage());
        	}
    	}
}
